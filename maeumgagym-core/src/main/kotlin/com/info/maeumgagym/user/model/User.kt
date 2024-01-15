package com.info.maeumgagym.user.model

import java.time.LocalDate
import java.util.UUID

data class User(
    val id: UUID = UUID(0, 0),
    var nickname: String,
    val roles: MutableList<Role> = mutableListOf(),
    val oauthId: String,
    var profileImage: String? = null,
    var isDeleted: Boolean = false,
    var lastSaved: LocalDate? = null,
    var dayCount: Long = 0,
    var todayWaka: Long = 0,
    var waka: Long = 0
)
