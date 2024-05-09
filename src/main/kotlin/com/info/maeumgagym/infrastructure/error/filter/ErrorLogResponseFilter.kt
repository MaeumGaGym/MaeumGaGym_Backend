package com.info.maeumgagym.infrastructure.error.filter

import com.info.maeumgagym.common.exception.*
import com.info.maeumgagym.infrastructure.error.logger.ErrorLogger
import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import com.info.maeumgagym.infrastructure.response.writer.DefaultHttpServletResponseWriter
import com.info.maeumgagym.infrastructure.response.writer.ErrorLogHttpServletResponseWriter
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.NestedServletException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * [Exception]이 발생했을 때, 응답 및 로그를 작성
 *
 * [doFilter], 정확히는 [doFilterInternal]를 *try*문으로 감싸 실행.
 * 이후 발생한 모든 예외를 *catch*해 예외를 [ErrorInfo]로 가공하고, 로그 및 응답을 작성.
 * [ErrorLogger]에서 로그를 작성하고, [ErrorLogHttpServletResponseWriter]에서 응답을 작성함.
 *
 * 원래 [DispatcherServlet][org.springframework.web.servlet.DispatcherServlet] 통과 이후 발생한 예외는 [NestedServletException.cause]로 감싸져 *throw*되지만, [ExceptionConvertFilter]에서 이를 [MaeumGaGymException]의 하위 타입으로 변환함. 자세한 것은 [ExceptionConvertFilter] 참조.
 *
 * 해당 *Filter*의 순서 설정 정보는 [ApplicationFilterChainConfig][com.info.maeumgagym.infrastructure.filter.config.ApplicationFilterChainConfig]에 존재
 *
 * @see ErrorLogger
 * @see ExceptionConvertFilter
 * @see ErrorLogHttpServletResponseWriter
 *
 * @author Daybreak312
 * @since 22.02.2024
 */
class ErrorLogResponseFilter(
    private val defaultHttpServletResponseWriter: DefaultHttpServletResponseWriter,
    private val errorLogHttpServletResponseWriter: ErrorLogHttpServletResponseWriter,
    private val errorLogger: ErrorLogger
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: MaeumGaGymException) {
            resolveException(e, response)
        } catch (e: Exception) {
            resolveException(
                CriticalException("Unknown Type Exception Came to ${javaClass.name}").apply { initCause(e) },
                response
            )
        }
    }

    private fun resolveException(e: MaeumGaGymException, response: HttpServletResponse) {
        if (isSuccessStatusCode(e.status)) {
            defaultHttpServletResponseWriter.doDefaultSettingWithStatusCode(response, e.status)
            return
        }

        val errorInfo = ErrorInfo.of(e)

        if (isUnknownMaeumGaGymException(e)) {
            e.printStackTrace()
        }

        errorLogHttpServletResponseWriter.writeErrorResponse(response, errorInfo)

        errorLogger.printLog(errorInfo)
    }

    private fun isSuccessStatusCode(status: Int): Boolean =
        status in 200..299

    private fun isUnknownMaeumGaGymException(e: MaeumGaGymException): Boolean =
        e !is BusinessLogicException && e !is SecurityException &&
            e !is FilterException && e !is InterceptorException &&
            e !is AuthenticationException && e !is PresentationValidationException
}
