package com.info.maeumgagym.goal.model

import com.info.maeumgagym.user.model.User
import java.time.LocalDate

data class Goal(
    val user: User,
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
