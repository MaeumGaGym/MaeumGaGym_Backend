package com.info.maeumgagym.auth.handler

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.auth.port.out.RevokeTokensPort
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomLogoutHandler(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val revokeTokensPort: RevokeTokensPort
): LogoutHandler {
    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val user = readCurrentUserPort.readCurrentUser()
        revokeTokensPort.revoke(user.oauthId)
    }
}
