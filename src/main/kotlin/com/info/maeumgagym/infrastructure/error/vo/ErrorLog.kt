package com.info.maeumgagym.infrastructure.error.vo

import com.info.maeumgagym.core.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.common.exception.PresentationValidationException
import java.time.LocalDateTime
import java.util.*

data class ErrorLog(
    val id: String = UUID.randomUUID().toString().substring(0 until 7),
    val exceptionClassName: String,
    val errorOccurredClassName: String,
    val status: Int = 500,
    val message: String? = null,
    val map: Map<String, String> = mapOf(),
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun of(e: Exception) =
            when (e) {
                is PresentationValidationException -> e.run {
                    com.info.maeumgagym.infrastructure.error.vo.ErrorLog(
                        exceptionClassName = javaClass.name,
                        errorOccurredClassName = com.info.maeumgagym.infrastructure.error.vo.getErrorOccurredClassName(
                            stackTrace.toList()
                        ),
                        status = status,
                        message = message,
                        map = fields
                    )
                }

                is MaeumGaGymException -> e.run {
                    com.info.maeumgagym.infrastructure.error.vo.ErrorLog(
                        exceptionClassName = javaClass.name,
                        errorOccurredClassName = com.info.maeumgagym.infrastructure.error.vo.getErrorOccurredClassName(
                            stackTrace.toList()
                        ),
                        status = status,
                        message = message
                    )
                }

                else -> e.run {
                    com.info.maeumgagym.infrastructure.error.vo.ErrorLog(
                        exceptionClassName = javaClass.name,
                        errorOccurredClassName = com.info.maeumgagym.infrastructure.error.vo.getErrorOccurredClassName(
                            stackTrace.toList()
                        ),
                        message = message
                    )
                }
            }
    }

    override fun toString() =
        "[$id] $status : \"$message\" in > $errorOccurredClassName < cause \"$exceptionClassName\""
}

private fun getErrorOccurredClassName(stackTrace: List<StackTraceElement>): String =
    "\"${stackTrace[3].className}\" or \"${stackTrace[2].className}\" or \"${stackTrace[1].className}\""
