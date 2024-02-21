package com.info.maeumgagym.purpose.dto.request

import java.time.LocalDate

data class UpdatePurposeRequest(
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
