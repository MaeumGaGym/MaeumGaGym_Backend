package com.info.maeumgagym.security.access.checker

import com.info.maeumgagym.common.exception.AuthenticationException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

/**
 * [AnnotationBasedUserAuthenticationChecker]의 구현체에서 사용되는 일부 중복 코드에 대해 추출된 함수 집합의 추상화
 *
 * @see AnnotationBasedUserAuthenticationChecker
 *
 * @author Daybreak312
 * @since 20-04-2024
 */
abstract class AbstractAnnotationBasedUserAuthenticationChecker
    : AnnotationBasedUserAuthenticationChecker {

    protected fun checkInvalidAuthentication() {
        if (SecurityContextHolder.getContext().authentication == null ||
            SecurityContextHolder.getContext().authentication is AnonymousAuthenticationToken
        ) {
            throw AuthenticationException.UNAUTHORIZED
        }
    }
}
