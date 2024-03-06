package com.info.maeumgagym.daily.dto.response

import java.time.LocalDateTime

data class DailyResponse(
    val title: String,
    val createAt: LocalDateTime,
    val url: String
)
