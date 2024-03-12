package com.info.maeumgagym.response.writer.impl

import com.info.maeumgagym.common.exception.PresentationValidationException
import com.info.maeumgagym.error.log.ErrorLog
import com.info.maeumgagym.response.writer.DefaultResponseWriter
import com.info.maeumgagym.response.writer.ErrorLogResponse
import com.info.maeumgagym.response.writer.ErrorLogResponseWriter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

/**
 * Docs는 상위 추상 클래스에 존재
 *
 * @author Daybreak312
 * @since 12-03-2024
 */
@Component
private class ErrorLogResponseWriterImpl(
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
