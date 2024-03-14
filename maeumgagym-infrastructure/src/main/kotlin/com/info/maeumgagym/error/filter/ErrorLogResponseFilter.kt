package com.info.maeumgagym.error.filter

import com.info.maeumgagym.common.exception.*
import com.info.maeumgagym.error.log.ErrorLog
import com.info.maeumgagym.global.config.filter.FilterChainConfig
import com.info.maeumgagym.response.writer.DefaultHttpServletResponseWriter
import com.info.maeumgagym.response.writer.ErrorLogHttpServletResponseWriter
import org.apache.catalina.core.ApplicationFilterChain
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.util.NestedServletException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * [Exception]이 발생했을 때, [ErrorLog] 및 [ErrorLogResponse]를 작성
 *
 * [doFilter], 정확히는 [doFilterInternal]를 *try*문으로 감싸 실행.
 * 이후 발생한 모든 예외를 *catch*해 각 예외에 따라 [ErrorLog]및 그에 대한 Response를 작성.
 * @see [ErrorLogHttpServletResponseWriter]
 *
 * 원래 [DispatcherServlet] 통과 이후 발생한 예외는 [NestedServletException.cause]로 감싸져 *throw*되지만, [ExceptionConvertFilter]에서 이를 [MaeumGaGymException]의 하위 타입으로 변환함. 자세한 것은 [ExceptionConvertFilter] 참조.
 *
 * 해당 *Filter*의 순서 설정 정보는 [FilterChainConfig]에 존재
 *
 * > 위의 사항이 기술적 문제로 잠시 반려되었습니다. 현재 [ApplicationFilterChain]이 아닌 [SecurityFilterChain]에 등록되어 있습니다.
 *
 * @see ExceptionConvertFilter
 */
class ErrorLogResponseFilter(
    private val defaultHttpServletResponseWriter: DefaultHttpServletResponseWriter,
    private val errorLogHttpServletResponseWriter: ErrorLogHttpServletResponseWriter
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: MaeumGaGymException) {
            if (isOkStatus(e.status)) {
                defaultHttpServletResponseWriter.doDefaultSettingWithStatusCode(response, e.status)
                return
            }

            val errorLog = printErrorLogAndReturn(e)

            if (isUnknownMaeumGaGymException(e)) {
                e.printStackTrace()
            }

            errorLogHttpServletResponseWriter.writeResponseWithErrorLogAndException(response, errorLog, e)
        } catch (e: Exception) {
            e.printStackTrace()
            val errorLog = printErrorLogAndReturn(e)
            errorLogHttpServletResponseWriter.writeResponseWithErrorLogAndException(response, errorLog, e)
        }
    }

    private fun isOkStatus(status: Int): Boolean =
        status in 200..299

    private fun isUnknownMaeumGaGymException(e: MaeumGaGymException): Boolean =
        e !is BusinessLogicException && e !is SecurityException &&
            e !is FilterException && e !is InterceptorException &&
            e !is AuthenticationException && e !is PresentationValidationException

    private fun printErrorLogAndReturn(e: Exception): ErrorLog {
        when (e) {
            is PresentationValidationException -> e.run {
                ErrorLog(
                    exceptionClassName = javaClass.name,
                    errorOccurredClassName = stackTrace[2].className + " or " + stackTrace[1].className,
                    status = status,
                    message = fields.map {
                        "${it.key}: ${it.value}"
                    }.toString()
                )
            }

            is MaeumGaGymException -> e.run {
                ErrorLog(
                    exceptionClassName = javaClass.name,
                    errorOccurredClassName = stackTrace[2].className + " or " + stackTrace[1].className,
                    status = status,
                    message = message
                )
            }

            else -> e.run {
                ErrorLog(
                    exceptionClassName = javaClass.name,
                    errorOccurredClassName = stackTrace[2].className + " or " + stackTrace[1].className,
                    message = message
                )
            }
        }.run {
            logger.info(
                "[$id] $status : \"$message\" in $errorOccurredClassName cause $exceptionClassName"
            )
            return this
        }
    }
}
