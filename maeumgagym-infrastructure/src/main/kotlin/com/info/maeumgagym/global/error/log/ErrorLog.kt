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
 * - [ErrorLogLayer.DOMAIN] : 비즈니스 로직에서 발생. 특히, [BusinessLogicException], [SecurityException]
 *
 * - [ErrorLogLayer.PRESENTATION] : Presentation 계층에서 Validation 실패로 인해 발생. 특히, [PresentationValidationException]
 *
 * - [ErrorLogLayer.FILTER] : Filter 실행 중 발생. 특히, [AuthenticationException], [FilterException]
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
         * [Exception]을 기준으로 [ErrorLogLayer]를 판별
         *
         * > Daybreak312 - [MaeumGaGymException]에 Layer를 명시하지 않은 이유 -
         * > [MaeumGaGymException]의 하위 타입으로 이미 예외의 종류를 이용한 여러 명시는 충분하다 생각했으며, [MaeumGaGymException]에서 [ErrorLog]와의 논리적 결합도를 형성할 필요가 없으며, 이를 저장하기 위한 [ErrorLogLayer]의 명시를 추가적으로 가질 이유 또한 없다고 판단했기에 이와 관련한 필드를 선언하지 않았습니다. 대신, 관련한 책임을 모두 [ErrorLog]와 관련한 필터, 클래스 등으로 이전해 [ErrorLogLayer]의 내장 함수로 예외에 따라 [ErrorLogLayer]를 결정할 수 있도록 했습니다. 다만, 일부 예외에 대하여 [ErrorLogLayer]가 정확히 기록되지 않을 수도 있습니다. [ErrorLog.layer]는 단순히 [ErrorLog]를 읽는 개발자를 위한 참고용이며, [ErrorLog.log]에 집중해주세요.
         *
         * @param e 기준삼을 [Exception]
         */
        fun of(e: Exception): ErrorLogLayer =
            when (e) {
                is BusinessLogicException, is SecurityException -> ErrorLogLayer.DOMAIN
                is FilterException, is AuthenticationException -> ErrorLogLayer.FILTER
                is InterceptorException -> ErrorLogLayer.INTERCEPTOR
                is PresentationValidationException -> ErrorLogLayer.PRESENTATION
                is CriticalException -> ErrorLogLayer.UNKNOWN
                else -> ErrorLogLayer.UNKNOWN
            }
    }
}
