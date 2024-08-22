package com.info.maeumgagym.security.authentication.checker

import com.info.maeumgagym.common.annotation.security.RequireRole
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.infrastructure.processor.annotation.WebAdapterMethodAnnotationProcessor
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import org.springframework.stereotype.Component

/**
 * [@RequireRole][RequireRole]에 대한 인증 확인자
 *
 * @author Daybreak312
 * @since 21-04-2024
 */
@Component
class RequireRoleChecker(
    private val authenticationManager: AuthenticationManager,
    private val readCurrentUserPort: ReadCurrentUserPort
) : WebAdapterMethodAnnotationProcessor {

    override fun preProcessing(annotations: List<Annotation>) {
        annotations.forEach { annotation ->
            if (annotation !is RequireRole) {
                return@forEach
            }

            if (authenticationManager.getAuthentication() == null) {
                throw AuthenticationException.UNAUTHORIZED
            }

            if (!readCurrentUserPort.readCurrentUser().roles.contains(annotation.role)) {
                throw AuthenticationException.ROLE_REQUIRED
            }
        }
    }

    override fun postProcessing(annotations: List<Annotation>) {}
}
