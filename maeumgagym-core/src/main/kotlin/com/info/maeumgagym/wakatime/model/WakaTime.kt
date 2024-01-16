package com.info.maeumgagym.wakatime.model

import com.info.maeumgagym.user.model.User
import java.time.LocalDate
import java.util.UUID

data class WakaTime (
    val user: User,
    val waka: Long,
    val date: LocalDate = LocalDate.now(),
)
