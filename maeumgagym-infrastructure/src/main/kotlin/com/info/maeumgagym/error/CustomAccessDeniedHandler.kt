package com.info.maeumgagym.error

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.security.env.CSRFProperties
import mu.KLogger
import mu.KotlinLogging
import org.springframework.core.log.LogMessage
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
    private val csrfProperties: CSRFProperties
) : AccessDeniedHandler {

    private var errorPage: String? = null

    private companion object {
        val logger: KLogger = KotlinLogging.logger {}
    }

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        if (response.isCommitted) {
            logger.trace { "Did not write to response since already committed" }
        } else if (this.errorPage == null) {
            logger.debug { "Responding with 403 status code" }
            //response.sendError(HttpStatus.FORBIDDEN.value(), "csrf token missing")

            val responseBody = objectMapper.writeValueAsString(
                request.getHeader(csrfProperties.header)?.run {
                    ErrorResponse(
                        403,
                        "Invalid CSRF Token"
                    )
                } ?: ErrorResponse(
                    403,
                    "CSRF Token Not in Possession"
                )
            )
            response.apply {
                contentType = "application/json"
                status = HttpStatus.FORBIDDEN.value()
                writer.write(responseBody)
                writer.flush()
            }
        } else {
            request.setAttribute("SPRING_SECURITY_403_EXCEPTION", accessDeniedException)
            response.status = HttpStatus.FORBIDDEN.value()
            if (logger.isDebugEnabled) {
                logger.debug {
                    LogMessage.format(
                        "Forwarding to %s with status code 403",
                        this.errorPage
                    )
                }
            }

            request.getRequestDispatcher(this.errorPage).forward(request, response)
        }
    }
}
