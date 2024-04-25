package com.info.maeumgagym.core.daily.model

import com.info.maeumgagym.core.user.model.User
import java.time.LocalDate
import java.time.LocalTime

data class Daily(
    val id: Long?,
    val title: String,
    val uploader: User,
    val date: LocalDate,
    val time: LocalTime
)
