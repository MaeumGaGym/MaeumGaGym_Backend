package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.routine.RoutineTestModule.saveInRepository
import com.info.maeumgagym.domain.routine.RoutineTestModule.setArchived
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import com.info.maeumgagym.domain.routine.repository.RoutineNativeRepository
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.error.TestException
import com.info.maeumgagym.routine.port.`in`.UpdateRoutineUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@SpringBootTest
internal class UpdateRoutineServiceTests @Autowired constructor(
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val routineRepository: RoutineRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val entityManager: EntityManager
) {

    private lateinit var user: UserJpaEntity
    private lateinit var routine: RoutineJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        routine = RoutineTestModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)
    }

    @Test
    fun updateMyRoutine() {
        entityManager.detach(routine)
        val request = RoutineTestModule.getUpdateRoutineRequest(routine)
        updateRoutineUseCase.updateRoutine(request, routine.id!!)
        Assertions.assertNotEquals(routine, routineRepository.findById(routine.id!!))
    }

    @Test
    fun updateOtherRoutine() {
        UserTestModule.createOtherUser().saveInRepository(userRepository).saveInContext(userMapper)
        TestException.assertThrowsMaeumGaGymExceptionInstance(SecurityException.PERMISSION_DENIED) {
            updateRoutineUseCase.updateRoutine(
                RoutineTestModule.getUpdateRoutineRequest(routine),
                routine.id!!
            )
        }
    }

    @Test
    fun updateNonExistentRoutine() {
        routineRepository.delete(routine)
        TestException.assertThrowsMaeumGaGymExceptionInstance(BusinessLogicException.ROUTINE_NOT_FOUND) {
            updateRoutineUseCase.updateRoutine(
                RoutineTestModule.getUpdateRoutineRequest(routine),
                routine.id!!
            )
        }
    }

    /**
     * @see UpdateRoutineUseCase.updateRoutine
     * @when 실패 상황 : 이미 다른 루틴이 할당된 요일로 루틴의 정보를 수정하려함
     * @fail 정상적으로 수정한 루틴이 저장되는지 확인
     * @fail 정상적으로 루틴이 저장되는지 확인
     * @fail 아래의 함수가 정상 작동하는지 확인
     *  @see RoutineNativeRepository.findByUserIdAndDayOfWeekAndIsArchivedFalse
     */
    @Test
    fun updateRoutineWithDayOfWeeksAlreadyOtherRoutineUsingAt() {
        // 기본 루틴을 수요일, 금요일, 토요일에 할당
        updateRoutineUseCase.updateRoutine(
            RoutineTestModule.getUpdateRoutineRequest(routine),
            routine.id!!
        )
        // 새로운 루틴 생성 (기본 루틴과 요일이 겹치지 않음)
        val otherRoutine = RoutineTestModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)

        // 새로운 루틴을 기본 루틴의 요일과 똑같이 변경 -> 예외 발생해야 함
        TestException.assertThrowsMaeumGaGymExceptionInstance(
            BusinessLogicException.OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK
        ) {
            updateRoutineUseCase.updateRoutine(
                RoutineTestModule.getUpdateRoutineRequest(otherRoutine),
                otherRoutine.id!!
            )
        }
    }

    /**
     * @see UpdateRoutineUseCase.updateRoutine
     * @when 성공 상황 : 이미 다른 루틴이 할당된 요일로 루틴의 정보를 수정하려 했으나, 그 루틴은 보관 상태
     * @fail 정상적으로 수정한 루틴이 저장되는지 확인
     * @fail 정상적으로 루틴이 저장되는지 확인
     * @fail 아래의 함수가 정상 작동하는지 확인
     *  @see RoutineNativeRepository.findByUserIdAndDayOfWeekAndIsArchivedFalse
     */
    @Test
    fun updateRoutineWithDayOfWeeksAlreadyOtherRoutineUsingAtButArchived() {
        // 기본 루틴을 수요일, 금요일, 토요일에 할당
        updateRoutineUseCase.updateRoutine(
            RoutineTestModule.getUpdateRoutineRequest(routine),
            routine.id!!
        )
        // 루틴 보관
        routine.setArchived(true).saveInRepository(routineRepository)
        // 새로운 루틴 생성 (기본 루틴과 요일이 겹치지 않음)
        val otherRoutine = RoutineTestModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)

        // 새로운 루틴을 기본 루틴의 요일과 똑같이 변경 -> 예외 발생해야 함
        Assertions.assertDoesNotThrow {
            updateRoutineUseCase.updateRoutine(
                RoutineTestModule.getUpdateRoutineRequest(otherRoutine),
                otherRoutine.id!!
            )
        }
    }
}
