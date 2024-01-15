package com.info.maeumgagym.wakatime.model

import com.info.maeumgagym.user.model.User
import java.time.LocalDateTime

data class WakaStarted(
    val user: User,
    val startAt: LocalDateTime
)
