package com.info.maeumgagym.domain.routine.junit5

import com.info.maeumgagym.domain.routine.module.RoutineFunctionsModule
import com.info.maeumgagym.domain.routine.module.RoutineFunctionsModule.saveInRepository
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.module.UserFunctionsModule
import com.info.maeumgagym.domain.user.module.UserFunctionsModule.saveInContext
import com.info.maeumgagym.domain.user.module.UserFunctionsModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.routine.service.ReadMyAllRoutineService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Transactional
@SpringBootTest
class ReadRoutineServiceTests @Autowired constructor(
    private val readMyAllRoutineService: ReadMyAllRoutineService,
    private val routineRepository: RoutineRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    private lateinit var user: UserJpaEntity
    private lateinit var otherUser: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserFunctionsModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        otherUser = UserFunctionsModule.createOtherUser().saveInRepository(userRepository)
    }

    @Test
    fun readMyAllRoutine() {
        var myRoutineSize = 0
        var otherRoutineSize = 0
        for (i in 1..10) {
            if (Random.nextInt(0, 1) == 1) {
                RoutineFunctionsModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)
                myRoutineSize++
            } else {
                RoutineFunctionsModule.createTestRoutine(otherUser.id!!).saveInRepository(routineRepository)
                otherRoutineSize++
            }
        }
        Assertions.assertEquals(readMyAllRoutineService.readAllMyRoutine().routineList.size, myRoutineSize)
    }

    // @Test
    fun readSharedRoutine() {
        TODO("루틴 단일 조회 기능 구현 후 생성")
    }

    // @Test
    fun readNotSharedRoutine() {
        TODO("루틴 단일 조회 기능 구현 후 생성")
    }
}
