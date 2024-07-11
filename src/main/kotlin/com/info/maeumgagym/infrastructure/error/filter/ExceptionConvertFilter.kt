package com.info.maeumgagym.infrastructure.error.filter

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.PresentationValidationException
import com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.util.NestedServletException
import java.time.DateTimeException
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.validation.ConstraintViolationException

/**
 * [doFilter]를 *try~catch*문으로 감싼 형태로 실행해 예외를 [MaeumGaGymException]으로 변환하는 작업을 실행
 *
 * [DispatcherServlet][org.springframework.web.servlet.DispatcherServlet] 이후에 발생한 예외는 [NestedServletException.cause]로 감싸져 전달됨.
 * 이 예외의 타입을 확인하고 해당 타입에 맞는 다른 예외로 변환해 *throw*
 *
 * 이것에 대한 응답 및 로그 작성의 책임은 [ErrorResolveFilter]에서 담당
 *
 * ```
 * catch (e: [NestedServletException]) { ... }
 * ```
 * - [MaeumGaGymException] 및 그 하위 타입일 경우 [MaeumGaGymException]으로 변환.
 * - Presentation 계층에서 Validation이 실패했을 경우 발생하는 예외 중 하나일 경우 [PresentationValidationException]으로 변환; 변환되는 타입 : [MethodArgumentNotValidException], [ConstraintViolationException], [MissingServletRequestParameterException]
 * - 그 외에는 일반 [MaeumGaGymException]으로 감싸짐
 *
 * 해당 *Filter*의 순서 설정 정보는 [ApplicationFilterChainConfig][com.info.maeumgagym.infrastructure.filter.config.ApplicationFilterChainConfig]에 존재
 *
 * @see ErrorResolveFilter
 *
 * @author Daybreak312
 * @since 22.02.2024
 */
class ExceptionConvertFilter(
    private val exceptionRepository: ExceptionRepository
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
            exceptionRepository.throwIt()
        } catch (e: MaeumGaGymException) {
            throw e
        } catch (e: NestedServletException) {
            throw when (e.cause) {
                is MaeumGaGymException -> e.cause as MaeumGaGymException

                is MethodArgumentNotValidException ->
                    (e.cause as MethodArgumentNotValidException).run {
                        PresentationValidationException(
                            400,
                            "field : ${fieldError?.field}\nparameter : $parameter",
                            mapOf()
                        ).apply { initCause(e) }
                    }

                is ConstraintViolationException ->
                    (e.cause as ConstraintViolationException).run {
                        PresentationValidationException(
                            status = 400,
                            message = message ?: "",
                            fields = constraintViolations.associate {
                                Pair<String, String>(it.propertyPath.toString(), it.message)
                            }
                        ).apply { initCause(e) }
                    }

                is MissingServletRequestParameterException ->
                    (e.cause as MissingServletRequestParameterException).run {
                        PresentationValidationException(
                            status = 400,
                            message = message,
                            fields = mapOf(Pair(parameterName, localizedMessage))
                        ).apply { initCause(e) }
                    }

                else -> convertThrowableToMaeumgagymException((e.cause ?: e))
            }
        } catch (e: DateTimeException) {
            throw PresentationValidationException(
                status = 400,
                message = "DateTime Format Wrong",
                fields = mapOf()
            ).apply { initCause(e) }
        } catch (e: Throwable) {
            throw convertThrowableToMaeumgagymException(e)
        }
    }

    private fun convertThrowableToMaeumgagymException(e: Throwable): MaeumGaGymException =
        MaeumGaGymException(
            500,
            e.message ?: e.localizedMessage ?: ("Cause Exception Class : " + e.javaClass.name),
            MaeumGaGymException.INTERNAL_SERVER_ERROR.responseMessage
        ).apply { initCause(e) }
}
