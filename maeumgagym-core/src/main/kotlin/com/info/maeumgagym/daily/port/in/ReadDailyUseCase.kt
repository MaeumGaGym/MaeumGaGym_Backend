package com.info.maeumgagym.daily.port.`in`

import com.info.maeumgagym.daily.dto.response.DailyListResponse

interface ReadDailyUseCase {

    fun readDailies(page: Int, size: Int): DailyListResponse
}
