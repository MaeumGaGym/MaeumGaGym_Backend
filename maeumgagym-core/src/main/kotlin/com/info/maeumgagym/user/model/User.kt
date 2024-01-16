package com.info.maeumgagym.user.model

import java.time.LocalDateTime
import java.util.*

data class User(
    val id: UUID = UUID(0, 0),
    val nickname: String,
    val roles: MutableList<Role> = mutableListOf(),
    val oauthId: String,
    val profileImage: String? = null,
    val wakaStartedAt: LocalDateTime? = null,
    val isDeleted: Boolean = false
)
