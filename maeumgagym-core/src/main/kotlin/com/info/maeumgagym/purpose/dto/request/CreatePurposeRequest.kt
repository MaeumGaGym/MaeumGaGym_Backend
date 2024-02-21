package com.info.maeumgagym.purpose.dto.request

import java.time.LocalDate

data class CreatePurposeRequest(
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
