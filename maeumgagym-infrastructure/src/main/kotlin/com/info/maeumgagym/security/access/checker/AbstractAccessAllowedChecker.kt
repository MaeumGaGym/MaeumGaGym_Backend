package com.info.maeumgagym.security.access.checker

import com.info.maeumgagym.common.exception.AuthenticationException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.method.HandlerMethod
import kotlin.reflect.KClass

abstract class AbstractAccessAllowedChecker : AccessAllowedChecker {

    protected fun <A : Annotation> HandlerMethod.getAnnotationOrNull(annotation: KClass<A>): A? {
        this.method.annotations.forEach {
            if (it.annotationClass == annotation) return it as A
        }
        this.bean::class.annotations.forEach {
            if (it.annotationClass == annotation) return it as A
        }

        return null
    }

    protected fun checkInvalidAuthentication() {
        if (SecurityContextHolder.getContext().authentication == null ||
            SecurityContextHolder.getContext().authentication is AnonymousAuthenticationToken) {
            throw AuthenticationException.UNAUTHORIZED
        }
    }
}
