package com.info.maeumgagym.core.wakatime.model

import com.info.maeumgagym.core.user.model.User
import java.time.LocalDate
import java.util.UUID

data class WakaTime(
    val user: com.info.maeumgagym.core.user.model.User,
    val waka: Long,
    val date: LocalDate = LocalDate.now(),
    val id: UUID? = null
)
