package com.info.maeumgagym.wakatime.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.SaveUserPort
import com.info.maeumgagym.wakatime.exception.WakaStartedNotYetException
import com.info.maeumgagym.wakatime.model.WakaTime
import com.info.maeumgagym.wakatime.port.`in`.EndWakatimeUseCase
import com.info.maeumgagym.wakatime.port.out.ReadWakaTimeFromUserAndDatePort
import com.info.maeumgagym.wakatime.port.out.SaveWakaTimePort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@UseCase
@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [Exception::class])
internal class EndWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readWakaTimeFromUserAndDatePort: ReadWakaTimeFromUserAndDatePort,
    private val saveWakaTimePort: SaveWakaTimePort,
    private val saveUserPort: SaveUserPort
) : EndWakatimeUseCase {

    override fun endWakatime() {
        // 토큰으로 user불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 와카타임 시작 시간 불러오기
        val wakaStarted = user.wakaStartedAt ?: throw WakaStartedNotYetException

        val now = LocalDateTime.now()
        val nowDate = now.toLocalDate()

        while (wakaStarted.toLocalDate() < nowDate) {

            val seconds = Duration.between(
                wakaStarted,
                LocalDateTime.of(
                    wakaStarted.year,
                    wakaStarted.month,
                    wakaStarted.dayOfMonth,
                    23, // hour
                    0, // minute
                    0 // second
                )
            ).seconds

            saveWakatime(user, wakaStarted.toLocalDate(), seconds)

            wakaStarted.plusDays(1)
        }

        // 와카타임 시작시간 초기화
        user.run {
            saveUserPort.saveUser(
                User(
                    id = id,
                    nickname = nickname,
                    roles = roles,
                    oauthId = oauthId,
                    profileImage = profileImage,
                    wakaStartedAt = null,
                    isDeleted = isDeleted
                )
            )
        }
    }

    private fun saveWakatime(user: User, date: LocalDate, seconds: Long) {

        // 먼저 생성한 와카타임 있는지 확인
        val wakaTime = readWakaTimeFromUserAndDatePort.findByUserIdAndDate(user.id, date)
            ?.let {
                // 있으면 waka += seconds
                WakaTime(
                    user = it.user,
                    waka = it.waka + seconds,
                    date = it.date,
                    id = it.id
                )
            } ?: WakaTime(
            // 없으면 waka = seconds
            user = user,
            waka = seconds,
            date = date
        )

        // wakatime save
        saveWakaTimePort.save(wakaTime)
    }
}
