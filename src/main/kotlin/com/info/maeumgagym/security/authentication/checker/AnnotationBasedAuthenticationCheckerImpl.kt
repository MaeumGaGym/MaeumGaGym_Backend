package com.info.maeumgagym.security.authentication.checker

import org.springframework.stereotype.Component

@Component
class AnnotationBasedAuthenticationCheckerImpl(
    private val requireAuthenticationChecker: RequireAuthenticationChecker,
    private val requireRoleChecker: RequireRoleChecker
) : AnnotationBasedAuthenticationChecker {

    override fun checkAuthentication(annotations: List<Annotation>) {
        requireAuthenticationChecker.preProcessing(annotations)
        requireRoleChecker.preProcessing(annotations)
    }
}
