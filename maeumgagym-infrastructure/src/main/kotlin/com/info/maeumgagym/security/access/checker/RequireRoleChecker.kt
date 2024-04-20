package com.info.maeumgagym.security.access.checker

import com.info.common.security.RequireRole
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import javax.servlet.http.HttpServletRequest

/**
 * [@RequireRole][RequireRole]이 부착된 Handler에 대한 접근 허가 확인자
 *
 * @author Daybreak312
 * @since 21-04-2024
 */
@Component
class RequireRoleChecker(
    private val readCurrentUserPort: ReadCurrentUserPort
) : AbstractAccessAllowedChecker() {

    override fun check(request: HttpServletRequest, handler: HandlerMethod) {
        val annotation = handler.getAnnotationOrNull(RequireRole::class) ?: return

        checkInvalidAuthentication()

        readCurrentUserPort.readCurrentUser().roles.forEach {
            if (it.name == annotation.role) return
        }

        throw AuthenticationException.ROLE_REQUIRED
    }
}
