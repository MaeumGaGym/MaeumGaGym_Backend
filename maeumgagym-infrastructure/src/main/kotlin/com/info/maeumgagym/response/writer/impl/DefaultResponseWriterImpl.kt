package com.info.maeumgagym.response.writer.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.response.writer.DefaultResponseWriter
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletResponse

@Component
private class DefaultResponseWriterImpl(
    private val objectMapper: ObjectMapper
) : DefaultResponseWriter() {

    override fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse =
        response.apply {
            this.writer.let {
                it.write(
                    objectMapper.writeValueAsString(`object`)
                )
                it.flush()
            }
        }

    override fun setDefaultSetting(response: HttpServletResponse, status: Int): HttpServletResponse =
        response.apply {
            this.status = status
            this.characterEncoding = StandardCharsets.UTF_8.name()
            this.contentType = MediaType.APPLICATION_JSON_VALUE
        }
}
