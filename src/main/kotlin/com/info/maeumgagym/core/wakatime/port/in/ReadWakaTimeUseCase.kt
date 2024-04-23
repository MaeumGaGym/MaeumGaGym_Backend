package com.info.maeumgagym.core.wakatime.port.`in`

import com.info.maeumgagym.wakatime.dto.response.WakatimeResponse

interface ReadWakaTimeUseCase {

    fun readTotalSeconds(): WakatimeResponse

    fun readDailySeconds(): WakatimeResponse
}
