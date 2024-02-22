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

/**
 * [ErrorLog]가 어디에서 발생했는지에 대한 enum
 *
 *
 * - [ErrorLogLayer.DOMAIN] : 비즈니스 로직에서 발생. 특히, [DomainException]
 *
 * - [ErrorLogLayer.PRESENTATION] : Presentation 계층에서 Validation 실패로 인해 발생. 특히, [PresentationValidationException]
 *
 * - [ErrorLogLayer.FILTER] : Filter 실행 중 발생. 특히, [SecurityException], [FilterException]
 *
 * - [ErrorLogLayer.INTERCEPTOR] : Interceptor 실행 중 발생. 특히, [InterceptorException]
 *
 * - [ErrorLogLayer.UNKNOWN] : 알 수 없는 위치에서 발생.
 */
enum class ErrorLogLayer(
    val value: String
) {

    DOMAIN("Domain"),
    PRESENTATION("Presentation"),
    FILTER("Filter"),
    INTERCEPTOR("Interceptor"),
    UNKNOWN("Unknown");

    companion object {

        /**
         * [ErrorLogLayer.value]만으로 객체를 얻기 위해 사용
         *
         * @return 각 [value]에 따라 해당하는 값을 반환.
         *
         * 해당하는 값이 없을 경우 [ErrorLogLayer.UNKNOWN]을 반환.
         */
        fun of(name: String): ErrorLogLayer =
            when (name) {
                DOMAIN.value -> DOMAIN
                PRESENTATION.value -> PRESENTATION
                FILTER.value -> FILTER
                INTERCEPTOR.value -> INTERCEPTOR
                else -> UNKNOWN
            }

        /**
         * exception을 기준으로 판별
         *
         * @author Daybreak312
         * [MaeumGaGymException] 내부 필드에 layer를 작성하기 보단,
         */
        fun of(maeumGaGymException: MaeumGaGymException): ErrorLogLayer =
            when (maeumGaGymException) {
                is DomainException -> ErrorLogLayer.DOMAIN
                is FilterException, is SecurityException -> ErrorLogLayer.FILTER
                is InterceptorException -> ErrorLogLayer.INTERCEPTOR
                is PresentationValidationException -> ErrorLogLayer.PRESENTATION
                is CriticalException -> ErrorLogLayer.UNKNOWN
                else -> ErrorLogLayer.UNKNOWN
            }
    }
}
