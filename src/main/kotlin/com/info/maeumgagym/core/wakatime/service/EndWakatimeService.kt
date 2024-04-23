package com.info.maeumgagym.core.wakatime.service

import com.info.maeumgagym.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.user.port.out.SaveUserPort
import com.info.maeumgagym.core.wakatime.model.WakaTime
import com.info.maeumgagym.core.wakatime.port.`in`.EndWakatimeUseCase
import com.info.maeumgagym.core.wakatime.port.out.ReadWakaTimePort
import com.info.maeumgagym.core.wakatime.port.out.SaveWakaTimePort
import java.time.Duration
import java.time.LocalDateTime

@UseCase
internal class EndWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveUserPort: SaveUserPort,
    private val saveWakaTimePort: SaveWakaTimePort,
    private val readWakaTimePort: ReadWakaTimePort
) : EndWakatimeUseCase {

    override fun endWakatime() {
        // 토큰으로 user불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 와카타임 시작 시간 불러오기
        val wakaStarted = user.wakaStartedAt ?: throw BusinessLogicException(400, "Waka Time Not Started Yet.")

        val now = LocalDateTime.now()

        // 와카타임 구하기
        val seconds = Duration.between(wakaStarted, now).seconds

        saveUserPort.save(
            user.copy(totalWakaTime = user.totalWakaTime.plus(seconds))
        )

        // 와카타임 시작시간 초기화
        user.run {
            saveUserPort.save(
                com.info.maeumgagym.core.user.model.User(
                    id = id,
                    nickname = nickname,
                    roles = roles,
                    oauthId = oauthId,
                    profileImage = profileImage,
                    wakaStartedAt = null,
                    isDeletedAt = isDeletedAt,
                    physicalInfoModel = null
                )
            )
        }

        val date = now.toLocalDate()

        // 먼저 생성한 와카타임 있는지 확인
        val wakaTime = readWakaTimePort.readByUserIdAndDate(user.id!!, date)
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
