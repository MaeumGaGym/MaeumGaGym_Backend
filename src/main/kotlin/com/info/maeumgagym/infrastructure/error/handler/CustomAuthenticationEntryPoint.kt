package com.info.maeumgagym.error.handler

import com.info.maeumgagym.core.common.exception.AuthenticationException
import com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint(
    private val exceptionRepository: com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: org.springframework.security.core.AuthenticationException
    ) {
        try {
            throw if (request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
                AuthenticationException.UNAUTHORIZED
            } else {
                AuthenticationException.INVALID_TOKEN
            }
        } catch (e: AuthenticationException) {
            exceptionRepository.save(e)
        }
    }
}
