package com.info.maeumgagym.domain.routine.junit5

import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import com.info.maeumgagym.domain.routine.module.RoutineFunctionsModule
import com.info.maeumgagym.domain.routine.module.RoutineFunctionsModule.saveInRepository
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.module.UserFunctionsModule
import com.info.maeumgagym.domain.user.module.UserFunctionsModule.saveInContext
import com.info.maeumgagym.domain.user.module.UserFunctionsModule.saveInRepository
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
        user = UserFunctionsModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        routine = RoutineFunctionsModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)
    }

    @Test
    fun updateMyRoutine() {
        entityManager.detach(routine)
        val request = RoutineFunctionsModule.getUpdateRoutineRequest(routine)
        updateRoutineUseCase.updateRoutine(request, routine.id!!)
        Assertions.assertNotEquals(routine, routineRepository.findByIdOrNull(routine.id!!))
    }

    @Test
    fun updateOtherRoutine() {
        UserFunctionsModule.createOtherUser().saveInRepository(userRepository).saveInContext(userMapper)
        Assertions.assertThrows(PermissionDeniedException::class.java) {
            updateRoutineUseCase.updateRoutine(
                RoutineFunctionsModule.getUpdateRoutineRequest(routine),
                routine.id!!
            )
        }
    }

    @Test
    fun updateNonExistentRoutine() {
        routineRepository.deleteById(routine.id!!)
        Assertions.assertThrows(RoutineNotFoundException::class.java) {
            updateRoutineUseCase.updateRoutine(
                RoutineFunctionsModule.getUpdateRoutineRequest(routine),
                routine.id!!
            )
        }
    }
}
