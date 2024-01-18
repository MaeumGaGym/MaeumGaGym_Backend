package com.info.maeumgagym.domain.wakatime

import com.info.maeumgagym.domain.auth.AuthTestModule
import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.module.UserTestModule
import com.info.maeumgagym.domain.user.module.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.domain.wakatime.exception.WakatimeDoesNotSavedException
import com.info.maeumgagym.domain.wakatime.module.WakatimeTestModule
import com.info.maeumgagym.domain.wakatime.module.WakatimeTestModule.setWakaStartedAtToBefore10Seconds
import com.info.maeumgagym.domain.wakatime.repository.WakaTimeRepository
import com.info.maeumgagym.wakatime.exception.AlreadyWakaStartedException
import com.info.maeumgagym.wakatime.exception.WakaStartedNotYetException
import com.info.maeumgagym.wakatime.port.`in`.EndWakatimeUseCase
import com.info.maeumgagym.wakatime.port.`in`.StartWakatimeUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional
@SpringBootTest
class WakatimeTests @Autowired constructor(
    private val startWakatimeUseCase: StartWakatimeUseCase,
    private val endWakatimeUseCase: EndWakatimeUseCase,
    private val wakaTimeRepository: WakaTimeRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    private lateinit var user: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
    }

    @Test
    fun start() {
        startWakatimeUseCase.startWakatime()
        Assertions.assertNotNull(user.wakaStartedAt)
    }

    @Test
    fun startWhenAlreadyStarted() {
        startWakatimeUseCase.startWakatime()
        user.saveInContext(userMapper)
        Assertions.assertThrows(AlreadyWakaStartedException::class.java) {
            startWakatimeUseCase.startWakatime()
        }
    }

    @Test
    fun end() {
        startWakatimeUseCase.startWakatime()
        user.setWakaStartedAtToBefore10Seconds().saveInRepository(userRepository).saveInContext(userMapper)
        endWakatimeUseCase.endWakatime()

        Assertions.assertDoesNotThrow {
            WakatimeTestModule.isSimilarWithAllowableErrorSize(
                a = 10,
                b = wakaTimeRepository.findByIdOrNull(
                    WakaTimeJpaEntity.IdClass(user.id!!, LocalDate.now())
                )?.waka ?: throw WakatimeDoesNotSavedException,
                allowableErrorSize = 2
            )
        }
    }

    @Test
    fun endWhenNotStarted() {
        Assertions.assertThrows(WakaStartedNotYetException::class.java) {
            endWakatimeUseCase.endWakatime()
        }
    }
}
