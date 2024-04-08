package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.routine.RoutineTestModule.saveInRepository
import com.info.maeumgagym.domain.routine.entity.current.RoutineJpaEntity
import com.info.maeumgagym.domain.routine.repository.current.RoutineRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.error.TestException
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
//@SpringBootTest
internal class DeleteRoutineServiceTests @Autowired constructor(
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
        TestException.assertThrowsMaeumGaGymExceptionInstance(SecurityException.PERMISSION_DENIED) {
            deleteRoutineUseCase.deleteRoutine(routine.id!!)
        }
        Assertions.assertNotNull(routineRepository.findById(routine.id!!))
    }

    @Test
    fun deleteNonExistentRoutine() {
        routineRepository.delete(routine)
        TestException.assertThrowsMaeumGaGymExceptionInstance(BusinessLogicException.ROUTINE_NOT_FOUND) {
            deleteRoutineUseCase.deleteRoutine(routine.id!!)
        }
    }
}
