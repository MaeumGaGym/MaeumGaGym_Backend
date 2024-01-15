package com.info.maeumgagym.wakatime.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.wakatime.exception.AlreadyWakaStartedException
import com.info.maeumgagym.wakatime.model.WakaStarted
import com.info.maeumgagym.wakatime.port.`in`.StartWakatimeUseCase
import com.info.maeumgagym.wakatime.port.out.ExistsWakaStaredByIdPort
import com.info.maeumgagym.wakatime.port.out.SaveWakaStartedPort
import java.time.LocalDateTime

@UseCase
class StartWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val existsWakaStaredByIdPort: ExistsWakaStaredByIdPort,
    private val saveWakaStartedPort: SaveWakaStartedPort
) : StartWakatimeUseCase {

    override fun startWakatime() {

        // 토큰으로 user불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 이미 와카타임을 시작했는지 확인, 이미 시작했다면 -> Exception
        if (existsWakaStaredByIdPort.existsById(user.id)) throw AlreadyWakaStartedException

        // 와카타임 시작
        saveWakaStartedPort.save(
            WakaStarted(
                user = user,
                startAt = LocalDateTime.now()
            )
        )
    }
}
