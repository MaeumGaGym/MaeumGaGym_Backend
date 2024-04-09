/*
package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.routine.RoutineTestModule.saveInRepository
import com.info.maeumgagym.domain.routine.RoutineTestModule.setArchived
import com.info.maeumgagym.domain.routine.repository.current.RoutineNativeRepository
import com.info.maeumgagym.domain.routine.repository.current.RoutineRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.error.TestException
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
//@SpringBootTest
internal class CreateRoutineServiceTests @Autowired constructor(
    private val createRoutineUseCase: CreateRoutineUseCase,
    private val routineRepository: RoutineRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    private lateinit var user: UserJpaEntity
    private lateinit var otherUser: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        otherUser = UserTestModule.createOtherUser().saveInRepository(userRepository)
    }

    @Test
    fun createRoutine() {
        createRoutineUseCase.createRoutine(
            RoutineTestModule.getCreateRoutineRequest()
        )
        Assertions.assertNotNull(
            routineRepository.findAllByUserId(user.id!!)[0]
        )
    }

//    @Test
//    fun createRoutineWithEmptyExerciseInfoList() {
//        Assertions.assertThrows(ExerciseListCannotEmptyException::class.java) {
//            createRoutineUseCase.createRoutine(
//                RoutineTestModule.getCreateRoutineRequest().apply {
//                    exerciseInfoModelList.clear()
//                }
//            )
//        }
//    }

    */
/**
 * @see CreateRoutineUseCase.createRoutine
 * @when 실패 상황 : 이미 다른 루틴이 할당된 요일로 새로운 루틴을 생성하려 함
 * @fail 정상적으로 루틴이 저장되는지 확인
 * @fail 아래의 함수가 정상 작동하는지 확인
 *  @see RoutineNativeRepository.findByUserIdAndDayOfWeekAndIsArchivedFalse
 *//*

    @Test
    fun createRoutineWithDayOfWeeksAlreadyOtherRoutineUsingAt() {
        RoutineTestModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)

        TestException.assertThrowsMaeumGaGymExceptionInstance(
            BusinessLogicException.OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK
        ) {
            createRoutineUseCase.createRoutine(
                RoutineTestModule.getCreateRoutineRequest()
            )
        }
    }

    */
/**
 * @see CreateRoutineUseCase.createRoutine
 * @when 실패 상황 : 이미 다른 루틴이 할당된 요일로 새로운 루틴을 생성하려 함
 * @fail 정상적으로 루틴이 저장되는지 확인
 * @fail 아래의 함수가 정상 작동하는지 확인
 *  @see RoutineNativeRepository.findByUserIdAndDayOfWeekAndIsArchivedFalse
 *//*

    @Test
    fun createRoutineWithDayOfWeeksAlreadyOtherRoutineUsingAtButArchived() {
        RoutineTestModule.createTestRoutine(user.id!!).setArchived(true).saveInRepository(routineRepository)

        Assertions.assertDoesNotThrow {
            createRoutineUseCase.createRoutine(
                RoutineTestModule.getCreateRoutineRequest()
            )
        }
    }
}
*/
