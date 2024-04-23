package com.info.maeumgagym.security.authentication.resolver

import com.info.common.security.RequireAuthentication
import com.info.common.security.RequireRole
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.security.access.checker.AnnotationBasedUserAuthenticationChecker
import com.info.maeumgagym.user.model.User
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
