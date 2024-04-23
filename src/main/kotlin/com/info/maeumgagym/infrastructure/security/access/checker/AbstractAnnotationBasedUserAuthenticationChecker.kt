package com.info.maeumgagym.infrastructure.security.access.checker

import com.info.maeumgagym.core.common.exception.AuthenticationException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import kotlin.reflect.KClass

/**
 * [AnnotationBasedUserAuthenticationChecker]의 구현체에서 사용되는 일부 중복 코드에 대해 추출된 함수 집합의 추상화
 *
 * @see AnnotationBasedUserAuthenticationChecker
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
abstract class AbstractAnnotationBasedUserAuthenticationChecker : AnnotationBasedUserAuthenticationChecker {

    protected fun <A : Annotation> Any.getAnnotationOrNull(annotation: KClass<A>): A? {
        this::class.annotations.forEach {
            if (it.annotationClass == annotation) return it as A
        }

        return null
    }

    protected fun checkInvalidAuthentication() {
        if (SecurityContextHolder.getContext().authentication == null ||
            SecurityContextHolder.getContext().authentication is AnonymousAuthenticationToken
        ) {
            throw AuthenticationException.UNAUTHORIZED
        }
    }
}
