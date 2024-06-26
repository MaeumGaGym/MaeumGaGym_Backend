package com.info.maeumgagym.core.wakatime.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.wakatime.dto.response.WakatimeResponse
import com.info.maeumgagym.core.wakatime.port.`in`.ReadWakaTimeUseCase
import com.info.maeumgagym.core.wakatime.port.out.ReadWakaTimePort
import java.time.LocalDate

@ReadOnlyUseCase
internal class ReadWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readWakaTimePort: ReadWakaTimePort
) : ReadWakaTimeUseCase {

    override fun readTotalSeconds(): WakatimeResponse =
        WakatimeResponse(readCurrentUserPort.readCurrentUser().totalWakaTime)

    override fun readDailySeconds(): WakatimeResponse {
        val user = readCurrentUserPort.readCurrentUser()

        return readWakaTimePort.readByUserIdAndDate(user.id!!, LocalDate.now())
            ?.run {
                WakatimeResponse(waka)
            } ?: WakatimeResponse(0)
    }
}
