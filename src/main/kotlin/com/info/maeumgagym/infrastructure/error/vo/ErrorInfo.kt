package com.info.maeumgagym.infrastructure.error.vo

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.PresentationValidationException
import java.time.LocalDateTime
import java.util.*

data class ErrorInfo(
    val id: String = UUID.randomUUID().toString().substring(0 until 7),
    val exception: MaeumGaGymException,
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
                        exception = this,
                        errorOccurredClassName = getErrorOccurredClassName(stackTrace.toList()),
                        status = status,
                        message = message,
                        responseMessage = responseMessage,
                        map = fields
                    )
                }

                else -> e.run {
                    ErrorInfo(
                        exception = this,
                        errorOccurredClassName = getErrorOccurredClassName(stackTrace.toList()),
                        status = status,
                        message = message,
                        responseMessage = responseMessage
                    )
                }
            }
    }
}

private fun getErrorOccurredClassName(stackTrace: List<StackTraceElement>): List<String> {
    if (stackTrace.size < 5) {
        return stackTrace.map { it.className }
    }

    val classes = mutableSetOf<String>()

    var i = 0
    while (classes.size != 5) {
        classes.add(stackTrace[i].className)
        i++
    }

    return classes.toList()
}
