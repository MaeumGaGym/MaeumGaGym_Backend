package com.info.maeumgagym.infrastructure.error.handler

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
import com.info.maeumgagym.security.env.CSRFProperties
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Deprecated(message = "논리적으로 사용되지 않음")
@Component
class CustomAccessDeniedHandler(
    private val exceptionRepository: ExceptionRepository,
    private val csrfProperties: CSRFProperties
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        try {
            throw if (request.getHeader(csrfProperties.header) == null) {
                AuthenticationException(403, "CSRF Token Not in Possession")
            } else {
                AuthenticationException(403, "Invalid CSRF Token")
            }
        } catch (e: AuthenticationException) {
            exceptionRepository.save(e)
        }
    }
}
