package com.info.maeumgagym.security.handler

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.core.auth.port.out.RevokeTokensPort
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * [LogoutFilter][org.springframework.security.web.authentication.logout.LogoutFilter]에서, Logout 로직을 담당하는 Handler.
 *
 * @see CustomSuccessLogoutHandler
 *
 * @author HyunSu1768
 * @since 03-11-2024
 */
@Component
class CustomLogoutHandler(
    private val revokeTokensPort: RevokeTokensPort
) : LogoutHandler {

    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        authentication ?: throw AuthenticationException.UNAUTHORIZED

        if (authentication !is UserModelAuthentication) {
            throw AuthenticationException.UNAUTHORIZED
        }

        revokeTokensPort.revoke()
    }
}
