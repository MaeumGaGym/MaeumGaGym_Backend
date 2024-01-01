package com.info.maeumgagym.user.model

import java.time.LocalDate
import java.util.UUID

data class DeleteAt(
    val id: UUID,
    val date: LocalDate = LocalDate.now()
)
