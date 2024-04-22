package com.info.maeumgagym.security.access.manager

import com.info.maeumgagym.security.access.checker.AnnotationBasedUserAuthenticationChecker
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod

@Component
class AccessManagerImpl(
    private val annotationBasedUserAuthenticationChecker: AnnotationBasedUserAuthenticationChecker
) : AccessManager {

    override fun checkAccessAllowed(handler: Any) {
        val handlerMethod = castHandlerToHandlerMethodOrNull(handler) ?: return

        annotationBasedUserAuthenticationChecker.check(handlerMethod.bean)
        annotationBasedUserAuthenticationChecker.check(handlerMethod.method)
    }

    private fun castHandlerToHandlerMethodOrNull(handler: Any): HandlerMethod? {
        if (handler !is HandlerMethod) {
            return null
        }

        return handler
    }
}
