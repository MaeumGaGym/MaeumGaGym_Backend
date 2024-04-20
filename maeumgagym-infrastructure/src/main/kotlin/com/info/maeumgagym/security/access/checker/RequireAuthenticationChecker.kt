package com.info.maeumgagym.security.access.checker

import com.info.common.security.RequireAuthentication
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import javax.servlet.http.HttpServletRequest

@Component
class RequireAuthenticationChecker : AbstractAccessAllowedChecker() {

    override fun check(request: HttpServletRequest, handler: HandlerMethod) {
        handler.getAnnotationOrNull(RequireAuthentication::class) ?: return

        checkInvalidAuthentication()
    }
}
