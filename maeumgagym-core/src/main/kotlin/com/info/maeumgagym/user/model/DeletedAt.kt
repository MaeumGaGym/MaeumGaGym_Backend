package com.info.maeumgagym.user.model

import java.time.LocalDate
import java.util.UUID

data class DeletedAt(
    val userId: UUID,
    val date: LocalDate = LocalDate.now()
)
