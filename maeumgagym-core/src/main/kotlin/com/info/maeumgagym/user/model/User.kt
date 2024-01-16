package com.info.maeumgagym.user.model

import java.time.LocalDateTime
import java.util.*

data class User(
    val id: UUID = UUID(0, 0),
    var nickname: String,
    val roles: MutableList<Role> = mutableListOf(),
    val oauthId: String,
    var profileImage: String? = null,
    var wakaStartedAt: LocalDateTime? = null,
    var isDeleted: Boolean = false
)
