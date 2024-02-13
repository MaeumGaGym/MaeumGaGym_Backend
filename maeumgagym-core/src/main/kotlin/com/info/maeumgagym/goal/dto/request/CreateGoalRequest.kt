package com.info.maeumgagym.goal.dto.request

import java.time.LocalDate

data class CreateGoalRequest(
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
