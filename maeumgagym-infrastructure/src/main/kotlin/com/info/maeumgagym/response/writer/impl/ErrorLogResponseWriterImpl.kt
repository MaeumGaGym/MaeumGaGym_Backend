package com.info.maeumgagym.response.writer.impl

import com.info.maeumgagym.common.exception.PresentationValidationException
import com.info.maeumgagym.error.log.ErrorLog
import com.info.maeumgagym.response.writer.DefaultResponseWriter
import com.info.maeumgagym.response.writer.ErrorLogResponseWriter
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.servlet.http.HttpServletResponse

@Component
class ErrorLogResponseWriterImpl(
    private val defaultResponseWriter: DefaultResponseWriter
) : ErrorLogResponseWriter() {

    override fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse =
        defaultResponseWriter.setBody(response, `object`)

    override fun setDefaultSetting(response: HttpServletResponse, status: Int): HttpServletResponse =
        defaultResponseWriter.setDefaultSetting(response, status)

    override fun writeResponseWithErrorLogAndException(
        response: HttpServletResponse,
        errorLog: ErrorLog,
        e: Exception
    ): HttpServletResponse = response.apply {
        setDefaultSetting(response, errorLog.status)
        setBody(
            response = response,
            `object` = errorLog.let {
                ErrorLogResponse(
                    status = it.status,
                    message = it.message,
                    errorLogId = it.id,
                    timestamp = it.timestamp,
                    map = if (e is PresentationValidationException) e.fields else mapOf()
                )
            }
        )
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
 */
data class ErrorLogResponse(
    val status: Int,
    val message: String?,
    val errorLogId: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val map: Map<String, String> = mapOf()
)
