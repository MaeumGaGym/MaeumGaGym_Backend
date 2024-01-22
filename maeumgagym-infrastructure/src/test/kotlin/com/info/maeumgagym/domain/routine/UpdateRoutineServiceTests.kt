package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import com.info.maeumgagym.domain.routine.RoutineTestModule.saveInRepository
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.routine.exception.RoutineNotFoundException
import com.info.maeumgagym.routine.port.`in`.UpdateRoutineUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@SpringBootTest
class UpdateRoutineServiceTests @Autowired constructor(
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
        Assertions.assertThrows(PermissionDeniedException::class.java) {
            updateRoutineUseCase.updateRoutine(
                RoutineTestModule.getUpdateRoutineRequest(routine),
                routine.id!!
            )
        }
    }

    @Test
    fun updateNonExistentRoutine() {
        routineRepository.delete(routine)
        Assertions.assertThrows(RoutineNotFoundException::class.java) {
            updateRoutineUseCase.updateRoutine(
                RoutineTestModule.getUpdateRoutineRequest(routine),
                routine.id!!
            )
        }
    }
}
