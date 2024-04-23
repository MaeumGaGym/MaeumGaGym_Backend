package com.info.maeumgagym.infrastructure.security.authentication.resolver

import com.info.maeumgagym.common.security.RequireAuthentication
import com.info.maeumgagym.common.security.RequireRole
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.infrastructure.security.access.checker.AnnotationBasedUserAuthenticationChecker
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthenticationHandlerMethodArgumentResolver(
    private val annotationBasedUserAuthenticationChecker: AnnotationBasedUserAuthenticationChecker,
    private val readCurrentUserPort: ReadCurrentUserPort
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        if (parameter.parameterType != User::class.java) {
            return false
        }

        parameter.getParameterAnnotation(RequireRole::class.java)
            ?: parameter.getParameterAnnotation(RequireAuthentication::class.java)
            ?: return false
        return true
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User {
        annotationBasedUserAuthenticationChecker.check(parameter)

        return readCurrentUserPort.readCurrentUser()
    }
}
