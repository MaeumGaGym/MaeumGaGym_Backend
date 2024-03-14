package com.info.maeumgagym.error.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.error.ErrorResponse
import mu.KLogger
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    private companion object {
        val logger: KLogger = KotlinLogging.logger { }
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.debug { "Pre-authenticated entry point called. Rejecting access" }
        //response!!.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied")

        val responseBody = objectMapper.writeValueAsString(
            ErrorResponse(
                401,
                "Access Token Not in Possession"
            )
        )

        response.apply {
            contentType = "application/json"
            status = HttpStatus.UNAUTHORIZED.value()
            writer.write(responseBody)
            writer.flush()
        }
    }
}
