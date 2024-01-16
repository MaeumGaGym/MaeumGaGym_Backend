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
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@UseCase
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

        if (wakaStarted.toLocalDate().isBefore(LocalDate.now())) {
            saveWakaTimeWhenStartedYesterday(user)
        } else {
            saveWakaTimeWhenStartedToday(user)
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

    private fun saveWakaTimeWhenStartedToday(user: User) {
        val nowDate = LocalDate.now()

        val seconds = Duration.between(user.wakaStartedAt!!, LocalDateTime.now()).seconds

        saveWakaTimePort.save(readWakaTimeFromUserAndDatePort.findByUserAndDate(user, nowDate)?.let {
            WakaTime(
                user = it.user,
                waka = it.waka + seconds,
                date = it.date,
                isNew = it.isNew
            )
        } ?: WakaTime(
            // 없으면 waka = seconds
            user = user,
            waka = seconds,
            date = nowDate
        ))
    }

    private fun saveWakaTimeWhenStartedYesterday(user: User) {
        val nowDate = LocalDate.now()

        val seconds = Duration.between(
            user.wakaStartedAt!!,
            LocalDateTime.of(nowDate, LocalTime.of(0, 0))
        ).seconds

        saveWakaTimePort.save(
            readWakaTimeFromUserAndDatePort.findByUserAndDate(user, nowDate.minusDays(1))
                ?.let {
                    WakaTime(
                        user = it.user,
                        waka = it.waka + seconds,
                        date = it.date,
                        isNew = it.isNew
                    )
                } ?: WakaTime(
                user = user,
                waka = seconds,
                date = nowDate
            ))

        saveWakaTimePort.save(
            WakaTime(
                user = user,
                waka = Duration.between(
                    LocalDateTime.of(nowDate, LocalTime.of(0, 0)),
                    LocalDateTime.now()
                ).seconds,
                date = nowDate
            )
        )
    }
}
