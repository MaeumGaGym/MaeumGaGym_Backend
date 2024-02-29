package com.info.maeumgagym.purpose.dto.response

import java.time.LocalDate

data class PurposeInfoResponse(
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
