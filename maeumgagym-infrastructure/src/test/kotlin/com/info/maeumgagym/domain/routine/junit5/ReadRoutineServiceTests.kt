package com.info.maeumgagym.domain.routine.junit5

import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.routine.module.RoutineTestModule
import com.info.maeumgagym.domain.routine.module.RoutineTestModule.saveInRepository
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.module.UserTestModule
import com.info.maeumgagym.domain.user.module.UserTestModule.saveInContext
import com.info.maeumgagym.domain.user.module.UserTestModule.saveInRepository
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
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
        otherUser = UserTestModule.createOtherUser().saveInRepository(userRepository)
    }

    @Test
    fun readMyAllRoutine() {
        var myRoutineSize = 0
        var otherRoutineSize = 0
        for (i in 1..10) {
            if (Random.nextInt(0, 1) == 1) {
                RoutineTestModule.createTestRoutine(user.id!!).saveInRepository(routineRepository)
                myRoutineSize++
            } else {
                RoutineTestModule.createTestRoutine(otherUser.id!!).saveInRepository(routineRepository)
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
