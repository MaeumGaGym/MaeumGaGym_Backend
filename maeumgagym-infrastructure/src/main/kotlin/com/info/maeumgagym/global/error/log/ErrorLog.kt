package com.info.maeumgagym.global.error.log

import java.time.LocalDateTime
import java.util.*

data class ErrorLog(
    val id: String = UUID.randomUUID().toString().substring(0 until 7),
    val exceptionClassName: String,
    val errorOccurredClassName: String,
    val status: Int = 500,
    val message: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
