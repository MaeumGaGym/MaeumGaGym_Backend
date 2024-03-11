package com.info.maeumgagym.domain.routine

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.routine.RoutineTestModule.appendDayOfWeek
import com.info.maeumgagym.domain.routine.RoutineTestModule.deleteDayOfWeek
import com.info.maeumgagym.domain.routine.RoutineTestModule.saveInRepository
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.error.TestException
import com.info.maeumgagym.routine.port.`in`.ReadAllMyRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.ReadTodayRoutineUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.random.Random

import org.junit.jupiter.api.Assertions.*

@Transactional
@SpringBootTest
internal class ReadRoutineServiceTests @Autowired constructor(
    private val readMyAllRoutineUseCase: ReadAllMyRoutineUseCase,
    private val readTodayRoutineUseCase: ReadTodayRoutineUseCase,
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
        assertEquals(readMyAllRoutineUseCase.readAllMyRoutine().routineList.size, myRoutineSize)
    }

    // @Test
    fun readSharedRoutine() {
        TODO("루틴 단일 조회 기능 구현 후 생성")
    }

    // @Test
    fun readNotSharedRoutine() {
        TODO("루틴 단일 조회 기능 구현 후 생성")
    }

    @Test
    fun readTodayRoutine() {
        RoutineTestModule.createTestRoutine(user.id!!).appendDayOfWeek(LocalDate.now().dayOfWeek)
            .saveInRepository(routineRepository)

        assertNotNull(
            readTodayRoutineUseCase.readTodayRoutine()
        )
    }

    @Test
    fun readTodayRoutineButNotExists() {
        RoutineTestModule.createTestRoutine(user.id!!).deleteDayOfWeek(LocalDate.now().dayOfWeek)
            .saveInRepository(routineRepository)

        TestException.assertThrowsMaeumGaGymExceptionInstance(MaeumGaGymException.NO_CONTENT) {
            readTodayRoutineUseCase.readTodayRoutine()
        }
    }
}
