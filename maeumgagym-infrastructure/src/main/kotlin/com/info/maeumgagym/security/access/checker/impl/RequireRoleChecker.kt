package com.info.maeumgagym.security.access.checker.impl

import com.info.common.security.RequireRole
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.security.access.checker.AbstractAnnotationBasedUserAuthenticationChecker
import org.springframework.stereotype.Component

/**
 * [@RequireRole][RequireRole]에 대한 인증 확인자
 *
 * @author Daybreak312
 * @since 21-04-2024
 */
@Component
class RequireRoleChecker(
    private val readCurrentUserPort: ReadCurrentUserPort
) : AbstractAnnotationBasedUserAuthenticationChecker() {

    override fun check(`object`: Any) {
        val annotation = `object`.getAnnotationOrNull(RequireRole::class) ?: return

        checkInvalidAuthentication()

        readCurrentUserPort.readCurrentUser().roles.forEach {
            if (it.name == annotation.role) return
        }

        throw AuthenticationException.ROLE_REQUIRED
    }
}
