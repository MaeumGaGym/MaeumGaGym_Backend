package com.info.maeumgagym.infrastructure.response.writer.impl

import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import com.info.maeumgagym.infrastructure.response.writer.DefaultHttpServletResponseWriter
import com.info.maeumgagym.infrastructure.response.writer.ErrorLogHttpServletResponseWriter
import com.info.maeumgagym.infrastructure.response.writer.ErrorLogResponse
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

/**
 * Docs는 상위 추상 클래스에 존재
 *
 * @author Daybreak312
 * @since 12-03-2024
 */
@Component
private class ErrorLogHttpServletResponseWriterImpl(
    private val defaultHttpServletResponseWriter: DefaultHttpServletResponseWriter
) : ErrorLogHttpServletResponseWriter() {

    override fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse =
        defaultHttpServletResponseWriter.setBody(response, `object`)

    override fun doDefaultSettingWithStatusCode(
        response: HttpServletResponse,
        status: Int
    ): HttpServletResponse =
        defaultHttpServletResponseWriter.doDefaultSettingWithStatusCode(response, status)

    override fun writeErrorResponse(
        response: HttpServletResponse,
        errorInfo: ErrorInfo
    ): HttpServletResponse = response.apply {
        doDefaultSettingWithStatusCode(response, errorInfo.status)
        setBody(
            response = response,
            `object` = ErrorLogResponse.of(errorInfo)
        )
    }
}
