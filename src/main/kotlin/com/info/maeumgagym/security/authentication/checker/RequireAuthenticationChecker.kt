package com.info.maeumgagym.security.authentication.checker

import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.infrastructure.processor.annotation.WebAdapterMethodAnnotationProcessor
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import org.springframework.stereotype.Component

/**
 * [@RequireAuthentication][RequireAuthentication]에 대한 인증 확인자
 *
 * @author Daybreak312
 * @since 21-04-2024
 */
@Component
class RequireAuthenticationChecker(
    private val authenticationManager: AuthenticationManager
) : WebAdapterMethodAnnotationProcessor {

    override fun preProcessing(annotations: List<Annotation>) {
        annotations.forEach { annotation ->
            if (annotation !is RequireAuthentication) {
                return@forEach
            }

            if (authenticationManager.getAuthentication() == null) {
                throw AuthenticationException.UNAUTHORIZED
            }
        }
    }

    override fun postProcessing(annotations: List<Annotation>) {}
}
