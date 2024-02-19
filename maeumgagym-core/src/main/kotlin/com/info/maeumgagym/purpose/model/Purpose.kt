package com.info.maeumgagym.purpose.model

import com.info.maeumgagym.user.model.User
import java.time.LocalDate

data class Purpose(
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val user: User,
    val id: Long? = null
)
