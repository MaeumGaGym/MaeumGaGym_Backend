package com.info.maeumgagym.wakatime.model

import com.info.maeumgagym.user.model.User
import java.time.LocalDateTime
import java.util.UUID

data class WakaStarted(
    val id: UUID? = null,
    val user: User,
    val startAt: LocalDateTime
)
