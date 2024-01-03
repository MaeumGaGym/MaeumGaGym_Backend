package com.info.maeumgagym.domain.routine

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
import com.info.maeumgagym.routine.service.DeleteRoutineService
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
    private val deleteRoutineService: DeleteRoutineService,
    private val routineRepository: RoutineRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    private lateinit var user: UserJpaEntity
    private lateinit var otherUser: UserJpaEntity
    private lateinit var routine: RoutineJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserFunctionsModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        otherUser = UserFunctionsModule.createOtherUser().saveInRepository(userRepository)
        routine = RoutineFunctionsModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)
    }

    @Test
    fun deleteMyRoutine() {
        Assertions.assertDoesNotThrow {
            deleteRoutineService.deleteRoutine(routine.id!!)
        }
        Assertions.assertNull(routineRepository.findByIdOrNull(routine.id!!))
    }

    @Test
    fun deleteOtherRoutine() {
        otherUser.saveInContext(userMapper)
        Assertions.assertThrows(PermissionDeniedException::class.java) {
            deleteRoutineService.deleteRoutine(routine.id!!)
        }
        Assertions.assertNotNull(routineRepository.findByIdOrNull(routine.id!!))
    }

    @Test
    fun deleteNonExistentRoutine() {
        routineRepository.deleteById(routine.id!!)
        Assertions.assertThrows(RoutineNotFoundException::class.java) {
            deleteRoutineService.deleteRoutine(routine.id!!)
        }
    }
}
