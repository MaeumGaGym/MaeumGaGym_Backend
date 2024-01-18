package com.info.maeumgagym.domain.wakatime

import com.info.maeumgagym.domain.auth.AuthTestModule.saveInContext
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.domain.wakatime.exception.WakatimeDoesNotSavedException
import com.info.maeumgagym.domain.wakatime.WakatimeTestModule.setWakaStartedAtToBefore10Seconds
import com.info.maeumgagym.domain.wakatime.repository.WakaTimeRepository
import com.info.maeumgagym.scheduler.WakaTimeScheduler
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

/**
 * @see StartWakatimeUseCase
 * @see EndWakatimeUseCase
 * @see WakaTimeScheduler
 *
 * 두 UseCase를 번갈아 사용하는 경우가 잦아 Context의 초기화를 항상 확인할 것
 * User의 영속성 관리에 민감함
 */
@Transactional
@SpringBootTest
class WakatimeTests @Autowired constructor(
    private val startWakatimeUseCase: StartWakatimeUseCase,
    private val endWakatimeUseCase: EndWakatimeUseCase,
    private val wakaTimeScheduler: WakaTimeScheduler,
    private val wakaTimeRepository: WakaTimeRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    private lateinit var user: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository).saveInContext(userMapper)
    }

    /**
     * @see StartWakatimeUseCase.startWakatime
     * @when 성공 상황
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     */
    @Test
    fun start() {
        startWakatimeUseCase.startWakatime()
        Assertions.assertNotNull(user.wakaStartedAt)
    }

    /**
     * @see StartWakatimeUseCase.startWakatime
     * @when 실패 상황
     * @success 이미 시작한 상태에서 다시 시작하려 했으므로 예외 발생
     * @fail 해당 상황에 대한 예외 처리가 되어 있는지 확인
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     */
    @Test
    fun startWhenAlreadyStarted() {
        startWakatimeUseCase.startWakatime()
        user.saveInContext(userMapper)
        Assertions.assertThrows(AlreadyWakaStartedException::class.java) {
            startWakatimeUseCase.startWakatime()
        }
    }

    /**
     * @see EndWakatimeUseCase.endWakatime
     * @when 성공 상황 : 처음으로 와카타임을 저장함
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     * @fail 종료 시 8~12초(10초와 오차범위 2초 내외)의 와카타임이 정상적으로 저장되는지 확인
     */
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
        Assertions.assertNull(user.wakaStartedAt)
    }

    /**
     * @see EndWakatimeUseCase.endWakatime
     * @when 성공 상황 : n번째로 와카타임을 저장함
     * @fail 아래 테스트도 실패하는지 확인
     *  @see end
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     * @fail 종료 시 8~12초(10초와 오차범위 2초 내외)의 와카타임이 정상적으로 저장되는지 확인
     */
    @Test
    fun endWhenDidEnded() {
        startWakatimeUseCase.startWakatime()
        user.setWakaStartedAtToBefore10Seconds().saveInRepository(userRepository).saveInContext(userMapper)
        endWakatimeUseCase.endWakatime()

        user.saveInContext(userMapper)
        startWakatimeUseCase.startWakatime()
        user.setWakaStartedAtToBefore10Seconds().saveInRepository(userRepository).saveInContext(userMapper)
        endWakatimeUseCase.endWakatime()

        Assertions.assertDoesNotThrow {
            WakatimeTestModule.isSimilarWithAllowableErrorSize(
                a = 20,
                b = wakaTimeRepository.findByIdOrNull(
                    WakaTimeJpaEntity.IdClass(user.id!!, LocalDate.now())
                )?.waka ?: throw WakatimeDoesNotSavedException,
                allowableErrorSize = 3
            )
        }
    }

    /**
     * @see EndWakatimeUseCase.endWakatime
     * @when 실패 상황
     * @success 시작하지 않은 상태에서 종료를 시도했으므로 예외 발생
     * @fail 해당 상황에 대한 예외 처리가 되어 있는지 확인
     * @fail 다른 곳에서 wakaStartedAt이 주입되었는지 확인
     */
    @Teast
    fun endWhenNotStarted() {
        Assertions.assertThrows(WakaStartedNotYetException::class.java) {
            endWakatimeUseCase.endWakatime()
        }
    }

    /**
     * @see WakaTimeScheduler.restartAllWakaTime
     * @when 성공 상황 : 이전 날짜에 와카타임이 한 번도 저장된 적 없음
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     * @fail 재시작 시 8~12초(10초와 오차범위 2초 내외)의 와카타임이 정상적으로 저장되는지 확인
     */
    @Test
    fun runSchedulerWhenDidntEnded() {
        startWakatimeUseCase.startWakatime()
        user.setWakaStartedAtToBefore10Seconds().saveInRepository(userRepository).saveInContext(userMapper)
        wakaTimeScheduler.restartAllWakaTime()

        Assertions.assertDoesNotThrow {
            WakatimeTestModule.isSimilarWithAllowableErrorSize(
                a = 10,
                b = wakaTimeRepository.findByIdOrNull(
                    WakaTimeJpaEntity.IdClass(user.id!!, LocalDate.now().minusDays(1))
                )?.waka ?: throw WakatimeDoesNotSavedException,
                allowableErrorSize = 2
            )
        }

        Assertions.assertNotNull(user.wakaStartedAt)
    }

    /**
     * @see WakaTimeScheduler.restartAllWakaTime
     * @when 성공 상황 : 이전 날짜에 와카타임이 한 번 이상 저장된 적 있음
     * @fail 아래 테스트도 실패하는지 확인
     *  @see endWhenDidEnded
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     * @fail 재시작 시 8~12초(10초와 오차범위 2초 내외)의 와카타임이 정상적으로 저장되는지 확인
     */
    @Test
    fun runSchedulerWhenDidEnded() {
        startWakatimeUseCase.startWakatime()
        user.setWakaStartedAtToBefore10Seconds().saveInRepository(userRepository).saveInContext(userMapper)
        endWakatimeUseCase.endWakatime()
        user.saveInContext(userMapper)
        startWakatimeUseCase.startWakatime()
        user.setWakaStartedAtToBefore10Seconds().saveInRepository(userRepository).saveInContext(userMapper)
        wakaTimeScheduler.restartAllWakaTime()

        Assertions.assertDoesNotThrow {
            WakatimeTestModule.isSimilarWithAllowableErrorSize(
                a = 10,
                b = wakaTimeRepository.findByIdOrNull(
                    WakaTimeJpaEntity.IdClass(user.id!!, LocalDate.now().minusDays(1))
                )?.waka ?: throw WakatimeDoesNotSavedException,
                allowableErrorSize = 2
            )
        }

        Assertions.assertNotNull(user.wakaStartedAt)
    }

    /**
     * @see WakaTimeScheduler.restartAllWakaTime
     * @when 실패 상황
     * @success 와카타임이 재시작된 후 다시 시작하려 했으므로 예외 발생
     * @fail 아래 테스트도 실패하는지 확인
     *  @see startWhenAlreadyStarted
     *  @see runSchedulerWhenDidntEnded
     *  @see runSchedulerWhenDidEnded
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     */
    @Test
    fun runSchedulerAndStart() {
        startWakatimeUseCase.startWakatime()
        wakaTimeScheduler.restartAllWakaTime()
        user.saveInContext(userMapper)
        Assertions.assertThrows(AlreadyWakaStartedException::class.java) {
            startWakatimeUseCase.startWakatime()
        }
    }

    /**
     * @see WakaTimeScheduler.restartAllWakaTime
     * @when 성공 상황 : 와카타임이 재시작된 후 종료
     * @fail 아래 테스트도 실패하는지 확인
     *  @see end
     *  @see runSchedulerWhenDidntEnded
     *  @see runSchedulerWhenDidEnded
     * @fail User의 wakaStartedAt이 정상적으로 적용되는지 확인
     * @fail 변경된 User를 정상적으로 저장하는지 확인
     * @fail 해당 클래스의 필드 user가 영속성 관리를 받는지 확인
     *  @see user
     */
    @Test
    fun runSchedulerAndEnd() {
        startWakatimeUseCase.startWakatime()
        wakaTimeScheduler.restartAllWakaTime()

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
}
