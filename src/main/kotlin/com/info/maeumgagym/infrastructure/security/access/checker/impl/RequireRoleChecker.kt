package com.info.maeumgagym.security.access.checker.impl

import com.info.common.security.RequireRole
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.AuthenticationException
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
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val annotationCollection: com.info.maeumgagym.infrastructure.collection.annotation.AnnotationCollection
) : AbstractAnnotationBasedUserAuthenticationChecker() {

    override fun check(`object`: Any) {
        val annotation = annotationCollection.getAnnotationOrNull(`object`, RequireRole::class) ?: return

        checkInvalidAuthentication()

        readCurrentUserPort.readCurrentUser().roles.forEach {
            if (it.name == annotation.role) return
        }

        throw AuthenticationException.ROLE_REQUIRED
    }
}
