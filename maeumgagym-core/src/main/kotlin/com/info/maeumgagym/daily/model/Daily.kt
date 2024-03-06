package com.info.maeumgagym.daily.model

import com.info.maeumgagym.user.model.User
import java.time.LocalDate
import java.time.LocalTime

data class Daily(
    val id: Long?,
    val title: String,
    val uploader: User,
    val date: LocalDate,
    val time: LocalTime
)
