package com.info.maeumgagym.core.purpose.dto.response

import java.time.LocalDate

data class PurposeInfoResponse(
    val id: Long,
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
