package com.info.maeumgagym.wakatime.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.wakatime.dto.response.TotalWakatimeResponse
import com.info.maeumgagym.wakatime.port.`in`.ReadWakaTimeUseCase

@ReadOnlyUseCase
internal class ReadWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadWakaTimeUseCase {

    override fun readTotalSeconds(): TotalWakatimeResponse =
        TotalWakatimeResponse(readCurrentUserPort.readCurrentUser().totalWakaTime)
}
