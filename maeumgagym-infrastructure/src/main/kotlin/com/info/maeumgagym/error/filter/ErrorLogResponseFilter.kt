package com.info.maeumgagym.error.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.common.exception.*
import com.info.maeumgagym.error.log.ErrorLog
import org.apache.catalina.core.ApplicationFilterChain
import org.springframework.http.MediaType
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.util.NestedServletException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * [Exception]이 발생했을 때, [ErrorLog] 및 [ErrorLogResponse]를 작성
 *
 * [doFilter], 정확히는 [doFilterInternal]를 *try*문으로 감싸 실행.
 * 이후 발생한 모든 예외를 *catch*해 각 예외에 따라 [ErrorLog]및 [ErrorLogResponse]를 작성.
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
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: MaeumGaGymException) {
            val errorLog = printErrorLogAndReturn(e)

            if (e !is BusinessLogicException &&
                e !is SecurityException &&
                e !is FilterException &&
                e !is InterceptorException &&
                e !is AuthenticationException &&
                e !is PresentationValidationException
            ) {
                e.printStackTrace()
            }

            if (errorLog.exceptionClassName == PresentationValidationException::javaClass.name) {
                writeFieldErrorResponse(response, errorLog, e as PresentationValidationException)
            } else {
                writeCommonErrorResponse(response, errorLog)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val errorLog = printErrorLogAndReturn(e)
            writeCommonErrorResponse(response, errorLog)
        }
    }

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

    /**
     * 일반적인 예외에 대한 [ErrorLogResponse]를 작성
     *
     * 해당하는 예외들
     * - [BusinessLogicException]
     * - [FilterException]
     * - [InterceptorException]
     * - [AuthenticationException]
     * - [CriticalException]
     * - 그 외 [PresentationValidationException]을 제외한 나머지 [Exception]
     */
    @Throws(IOException::class)
    private fun writeCommonErrorResponse(response: HttpServletResponse, errorLog: ErrorLog) {
        response.errorResponseDefaultSetting(errorLog)
        response.writeErrorLogResponse(
            errorLog.run {
                ErrorLogResponse(status, message, id, timestamp)
            }
        )
    }

    /**
     * Presentation에서 Validation이 실패했을 떄의 예외에 대한 [ErrorLogResponse]를 작성
     *
     * 해당하는 예외
     * - [PresentationValidationException]
     */
    @Throws(IOException::class)
    private fun writeFieldErrorResponse(
        response: HttpServletResponse,
        errorLog: ErrorLog,
        e: PresentationValidationException
    ) {
        response.errorResponseDefaultSetting(errorLog)
        response.writeErrorLogResponse(
            errorLog.run {
                ErrorLogResponse(status, message, id, timestamp, e.fields)
            }
        )
    }

    /**
     * [ErrorLogResponse]를 작성할 때의 기본 정보들에 대한 설정
     */
    private fun HttpServletResponse.errorResponseDefaultSetting(errorLog: ErrorLog) {
        status = errorLog.status
        characterEncoding = StandardCharsets.UTF_8.name()
        contentType = MediaType.APPLICATION_JSON_VALUE
    }

    /**
     * [ErrorLogResponse]의 실제 작성 부분
     */
    private fun HttpServletResponse.writeErrorLogResponse(errorLogResponse: ErrorLogResponse) {
        writer.write(
            objectMapper.writeValueAsString(errorLogResponse)
        )
        writer.flush()
    }
}

/**
 * Error의 정보를 담고 있는 Response
 *
 * @param status 상태 코드.
 * @param message 예외 메세지.
 * @param errorLogId 저장된 에러 로그의 Key.
 * @param timestamp timestamp
 * @param map Field Error 등 경우에 따라 Key-Value의 정보가 추가적으로 필요할 경우 사용
 *  @see writeErrorLogResponse
 */
data class ErrorLogResponse(
    val status: Int,
    val message: String?,
    val errorLogId: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val map: Map<String, String> = mapOf()
)
