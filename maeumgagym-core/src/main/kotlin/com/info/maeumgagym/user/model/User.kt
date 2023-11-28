package com.info.maeumgagym.user.model

import java.util.UUID

data class User(
    val id: UUID = UUID(0, 0),
    val nickname: String,
    val roles: MutableList<Role> = mutableListOf()
)
