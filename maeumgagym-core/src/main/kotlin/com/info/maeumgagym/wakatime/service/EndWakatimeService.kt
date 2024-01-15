package com.info.maeumgagym.wakatime.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.wakatime.exception.WakaStartedNotYetException
import com.info.maeumgagym.wakatime.model.WakaTime
import com.info.maeumgagym.wakatime.port.`in`.EndWakatimeUseCase
import com.info.maeumgagym.wakatime.port.out.ReadWakaStartedFromUser
import com.info.maeumgagym.wakatime.port.out.ReadWakaTimeFromUserAndDatePort
import com.info.maeumgagym.wakatime.port.out.SaveWakaTimePort
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@UseCase
class EndWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readWakaStartedFromUser: ReadWakaStartedFromUser,
    private val readWakaTimeFromUserAndDatePort: ReadWakaTimeFromUserAndDatePort,
    private val saveWakaTimePort: SaveWakaTimePort
) : EndWakatimeUseCase {

    override fun endWakatime() {
        // 토큰으로 user불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 와카타임 시작 시간 불러오기
        val wakaStarted = readWakaStartedFromUser.findByUser(user)
            ?: throw WakaStartedNotYetException

        // 와카타임 구하기
        val seconds = Duration.between(wakaStarted.startAt, LocalDateTime.now()).seconds

        val now = LocalDate.now()

        // 먼저 생성한 와카타임 있는지 확인
        val wakaTime = readWakaTimeFromUserAndDatePort.findByUserAndDate(user, now)
            ?.let {
                // 있으면 waka += seconds
                WakaTime(
                    id = it.id,
                    user = it.user,
                    waka = it.waka + seconds,
                    date = it.date
                )
            } ?: WakaTime(
                // 없으면 waka = seconds
                user = user,
                waka = seconds,
                date = now
            )

        // wakatime save
        saveWakaTimePort.save(wakaTime)
    }
}
