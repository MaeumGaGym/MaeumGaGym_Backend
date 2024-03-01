package com.info.maeumgagym.purpose.port.`in`

import com.info.maeumgagym.purpose.dto.response.PurposeListResponse

interface ReadPurposesFromYearAndMonthUseCase {
    fun readPurposesFromYearAndMonth(year: Int, month: Int): PurposeListResponse
}
