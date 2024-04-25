package com.info.maeumgagym.core.daily.dto.response

import java.time.LocalDateTime

data class DailyResponse(
    val title: String,
    val createAt: LocalDateTime,
    val url: String
)
