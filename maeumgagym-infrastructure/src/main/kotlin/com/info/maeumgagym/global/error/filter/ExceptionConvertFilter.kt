package com.info.maeumgagym.global.error.filter

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.PresentationValidationException
import com.info.maeumgagym.global.error.filter.ErrorLogResponseFilter.ErrorLogResponse
import com.info.maeumgagym.global.error.log.ErrorLog
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.util.NestedServletException
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.validation.ConstraintViolationException

/**
 * [doFilter]를 *try~catch*문으로 감싼 형태로 실행해 예외를 [MaeumGaGymException]으로 변환하는 작업을 실행
 *
 * [DispatcherServlet] 이후에 발생한 예외는 [NestedServletException.cause]로 감싸져 전달됨.
 * 이 예외의 타입을 확인하고 해당 타입에 맞는 다른 예외로 변환해 *throw*
 *
 * 이것의 [ErrorLog]와 [ErrorLogResponse]는 [ErrorLogResponseFilter]에서 처리함
 *
 * ```
 * catch (e: [NestedServletException]) { ... }
 * ```
 * - [MaeumGaGymException] 및 그 하위 타입일 경우 [MaeumGaGymException]으로 변환.
 * - Presentation 계층에서 Validation이 실패했을 경우 발생하는 예외 중 하나일 경우 [PresentationValidationException]으로 변환; 변환되는 타입 : [MethodArgumentNotValidException], [ConstraintViolationException], [MissingServletRequestParameterException]
 * - 그 외에는 그대로 변환
 */
@Order(1)
@Component
class ExceptionConvertFilter : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
        } catch (e: NestedServletException) {
            throw when (e.cause) {
                is MaeumGaGymException -> e.cause as MaeumGaGymException

                is MethodArgumentNotValidException ->
                    (e.cause as MethodArgumentNotValidException).run {
                        PresentationValidationException(
                            400,
                            "field : ${fieldError?.field}\nparameter : $parameter",
                            mapOf()
                        )
                    }

                is ConstraintViolationException ->
                    (e.cause as ConstraintViolationException).run {
                        PresentationValidationException(
                            status = 400,
                            message = message ?: "",
                            fields = constraintViolations.associate {
                                Pair<String, String>(it.propertyPath.toString(), it.message)
                            }
                        )
                    }

                is MissingServletRequestParameterException ->
                    (e.cause as MissingServletRequestParameterException).run {
                        PresentationValidationException(
                            status = 400,
                            message = message,
                            fields = mapOf(Pair(parameterName, localizedMessage))
                        )
                    }

                else -> e.cause ?: e
            }
        }
    }
}
