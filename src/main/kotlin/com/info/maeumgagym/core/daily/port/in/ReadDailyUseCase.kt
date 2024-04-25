package com.info.maeumgagym.core.daily.port.`in`

import com.info.maeumgagym.core.daily.dto.response.DailyListResponse

interface ReadDailyUseCase {

    fun readDailies(page: Int, size: Int): DailyListResponse
}
