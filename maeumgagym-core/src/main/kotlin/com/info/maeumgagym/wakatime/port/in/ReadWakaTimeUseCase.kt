package com.info.maeumgagym.wakatime.port.`in`

import com.info.maeumgagym.wakatime.dto.response.TotalWakatimeResponse

interface ReadWakaTimeUseCase {

    fun readTotalSeconds(): TotalWakatimeResponse
}
