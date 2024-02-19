package com.info.maeumgagym.global.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.global.error.ErrorResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.time.DateTimeException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(0)
class MaeumGaGymExceptionFilter(
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
            writerErrorCode(response, e.errorCode)
        } catch (e: DateTimeException) {
            response.apply {
                status = HttpStatus.BAD_REQUEST.value()
                characterEncoding = StandardCharsets.UTF_8.name()
                contentType = MediaType.APPLICATION_JSON_VALUE
                writer.write(objectMapper.writeValueAsString(ErrorResponse(status, e.message)))
                writer.flush()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            writerErrorCode(response, ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    @Throws(IOException::class)
    private fun writerErrorCode(response: HttpServletResponse, errorCode: ErrorCode) {
        response.apply {
            status = errorCode.status
            characterEncoding = StandardCharsets.UTF_8.name()
            contentType = MediaType.APPLICATION_JSON_VALUE
            writer.write(objectMapper.writeValueAsString(ErrorResponse(errorCode.status, errorCode.message)))
            writer.flush()
        }
    }
}
