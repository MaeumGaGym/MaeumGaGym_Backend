package com.info.maeumgagym.global.error.log

import com.info.maeumgagym.common.exception.*
import java.time.LocalDateTime
import java.util.*

data class ErrorLog(
    val status: Int = 500,
    val message: String? = null,
    val log: String,
    val layer: ErrorLogLayer = ErrorLogLayer.UNKNOWN,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val id: String = UUID.randomUUID().toString().substring(0 until 7)
)

data class ErrorLogLayer(
    val name: String
) {
    companion object {
        val DOMAIN = ErrorLogLayer("Domain")
        val PRESENTATION = ErrorLogLayer("Presentation")
        val FILTER = ErrorLogLayer("Filter")
        val INTERCEPTOR = ErrorLogLayer("Interceptor")
        val UNKNOWN = ErrorLogLayer("Unknown")

        fun of(name: String): ErrorLogLayer =
            when (name) {
                DOMAIN.name -> DOMAIN
                PRESENTATION.name -> PRESENTATION
                FILTER.name -> FILTER
                INTERCEPTOR.name -> INTERCEPTOR
                else -> UNKNOWN
            }

        fun of(maeumGaGymException: MaeumGaGymException): ErrorLogLayer =
            when (maeumGaGymException) {
                is DomainException -> ErrorLogLayer.DOMAIN
                is FilterException -> ErrorLogLayer.FILTER
                is InterceptorException -> ErrorLogLayer.INTERCEPTOR
                is PresentationValidationException -> ErrorLogLayer.PRESENTATION
                is SecurityException -> ErrorLogLayer.FILTER
                is CriticalException -> ErrorLogLayer.UNKNOWN
                else -> ErrorLogLayer.UNKNOWN
            }
    }
}
