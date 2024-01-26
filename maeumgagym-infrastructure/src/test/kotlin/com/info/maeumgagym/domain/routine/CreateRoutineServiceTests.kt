package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
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
}
