package com.info.maeumgagym.infrastructure.response.writer.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.infrastructure.response.writer.DefaultHttpServletResponseWriter
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletResponse

/**
 * Docs는 상위 추상 클래스에 존재
 *
 * @author Daybreak312
 * @since 12-03-2024
 */
@Component
private class DefaultHttpServletResponseWriterImpl(
    private val objectMapper: ObjectMapper
) : DefaultHttpServletResponseWriter() {

    override fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse =
        response.apply {
            this.writer.let {
                it.write(
                    objectMapper.writeValueAsString(`object`)
                )
                it.flush()
            }
        }

    override fun doDefaultSettingWithStatusCode(
        response: HttpServletResponse,
        status: Int
    ): HttpServletResponse =
        response.apply {
            this.status = status
            this.characterEncoding = StandardCharsets.UTF_8.name()
            this.contentType = MediaType.APPLICATION_JSON_VALUE
        }
}
