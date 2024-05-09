package com.info.maeumgagym.infrastructure.error.vo

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.PresentationValidationException
import java.time.LocalDateTime
import java.util.*

data class ErrorInfo(
    val id: String = UUID.randomUUID().toString().substring(0 until 7),
    val exceptionClassName: String,
    val errorOccurredClassName: List<String>,
    val status: Int = 500,
    val message: String,
    val responseMessage: String,
    val map: Map<String, String> = mapOf(),
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun of(e: MaeumGaGymException) =
            when (e) {
                is PresentationValidationException -> e.run {
                    ErrorInfo(
                        exceptionClassName = javaClass.name,
                        errorOccurredClassName = getErrorOccurredClassName(
                            stackTrace.toList()
                        ),
                        status = status,
                        message = message,
                        responseMessage = responseMessage,
                        map = fields
                    )
                }

                else -> e.run {
                    ErrorInfo(
                        exceptionClassName = javaClass.name,
                        errorOccurredClassName = getErrorOccurredClassName(
                            stackTrace.toList()
                        ),
                        status = status,
                        message = message,
                        responseMessage = responseMessage,
                    )
                }
            }
    }
}

private fun getErrorOccurredClassName(stackTrace: List<StackTraceElement>): List<String> =
    listOf(
        "\"${stackTrace[3].className}\"",
        "\"${stackTrace[2].className}\"",
        "\"${stackTrace[1].className}\""
    )
