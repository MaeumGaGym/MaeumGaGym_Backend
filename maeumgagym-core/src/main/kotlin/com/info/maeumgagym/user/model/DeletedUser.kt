package com.info.maeumgagym.user.model

import java.time.LocalDate
import java.util.*

data class DeletedUser(
    val id: UUID? = null,
    val user: User,
    val deletedAt: LocalDate
)
