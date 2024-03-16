package com.info.maeumgagym.purpose.port.`in`

import com.info.maeumgagym.purpose.dto.response.PurposeListResponse
import java.time.LocalDate

interface ReadPurposesFromDateForRangeUseCase {

    fun readPurposesFromDateForRange(startDate: LocalDate): PurposeListResponse
}
