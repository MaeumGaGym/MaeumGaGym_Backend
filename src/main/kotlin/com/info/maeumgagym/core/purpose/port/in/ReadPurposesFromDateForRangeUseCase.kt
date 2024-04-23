package com.info.maeumgagym.core.purpose.port.`in`

import com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse
import java.time.LocalDate

interface ReadPurposesFromDateForRangeUseCase {

    fun readPurposesFromDateForRange(startDate: LocalDate): com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse
}
