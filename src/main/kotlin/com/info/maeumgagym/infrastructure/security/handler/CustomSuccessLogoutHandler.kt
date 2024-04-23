package com.info.maeumgagym.infrastructure.security.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * [LogoutFilter][org.springframework.security.web.authentication.logout.LogoutFilter]에서, [CustomLogoutHandler]가 Logout에 성공했을 때의 처리를 담당하는 Handler.
 *
 * @see CustomLogoutHandler
 *
 * @author HyunSu1768
 * @since 03-11-2024
 */
@Component
class CustomSuccessLogoutHandler : LogoutSuccessHandler {

    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        response.let {
            it.status = HttpServletResponse.SC_NO_CONTENT
            it.writer.flush()
        }
    }
}
