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
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class DeleteRoutineServiceTests @Autowired constructor(
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val routineRepository: RoutineRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    private lateinit var user: UserJpaEntity
    private lateinit var otherUser: UserJpaEntity
    private lateinit var routine: RoutineJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        otherUser = UserTestModule.createOtherUser().saveInRepository(userRepository)
        routine = RoutineTestModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)
    }

    @Test
    fun deleteMyRoutine() {
        Assertions.assertDoesNotThrow {
            deleteRoutineUseCase.deleteRoutine(routine.id!!)
        }
        Assertions.assertNull(routineRepository.findById(routine.id!!))
    }

    @Test
    fun deleteOtherRoutine() {
        otherUser.saveInContext(userMapper)
        Assertions.assertThrows(PermissionDeniedException::class.java) {
            deleteRoutineUseCase.deleteRoutine(routine.id!!)
        }
        Assertions.assertNotNull(routineRepository.findById(routine.id!!))
    }

    @Test
    fun deleteNonExistentRoutine() {
        routineRepository.delete(routine)
        Assertions.assertThrows(RoutineNotFoundException::class.java) {
            deleteRoutineUseCase.deleteRoutine(routine.id!!)
        }
    }
}
