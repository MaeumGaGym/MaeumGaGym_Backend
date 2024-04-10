package com.info.maeumgagym.security.authentication.interceptor

import com.info.common.security.RequireRole
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.collection.AnnotationBeanCollection
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.user.model.Role
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.reflect.Method
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.KClass

/**
 * 서버의 커스텀 인증 Interceptor.
 *
 * [JwtFilter][com.info.maeumgagym.security.jwt.JwtFilter]에서 인가되어 온 [Authentication][com.info.maeumgagym.security.authentication.vo.UserModelAuthentication.user]에 존재하는 [User][com.info.maeumgagym.user.model.User]가 소유하고 있는 [Role][com.info.maeumgagym.user.model.Role]이 클라이언트가 요구하는 리소스에 적합한지 확인하기 위한 Interceptor.
 *
 * @author Daybreak312
 * @since 09-04-2024
 */
class RoleAuthenticationInterceptor(
    private val annotationBeanCollection: AnnotationBeanCollection,
    private val readCurrentUserPort: ReadCurrentUserPort
) : HandlerInterceptor {

    private lateinit var needRoleControllers: Map<String, Any>

    private var initialed: Boolean = false

    /**
     * 객체 초기화.
     *
     * 객체 생성 순서의 비 연관성으로 인해, 모든 객체가 생성된 이후 Bean 탐색을 위한 lateinit
     */
    private fun initialize() {
        needRoleControllers = annotationBeanCollection.getBeans(RequireRole::class as KClass<Annotation>)
        initialed = true
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (!initialed) initialize()

        val handlerMethod = castHandlerToHandlerMethodOrNull(handler) ?: return true

        val roleNeed = getRequiredRoleIfNeed(handlerMethod) ?: return true

        val currentUser = readCurrentUserPort.readCurrentUser()

        if (!currentUser.roles.contains(roleNeed)) {
            throw AuthenticationException.ROLE_REQUIRED
        }

        return true
    }

    private fun castHandlerToHandlerMethodOrNull(handler: Any): HandlerMethod? {
        if (handler !is HandlerMethod) {
            return null
        }

        return handler
    }

    private fun getRequiredRoleIfNeed(handlerMethod: HandlerMethod): Role? =
        if (isClassHaveRoleAnnotation(handlerMethod.bean)) {
            getRequiredRoleInRoleAnnotation(handlerMethod.bean)
        } else if (isMethodHaveRoleAnnotation(handlerMethod.method)) {
            getRequiredRoleInRoleAnnotation(handlerMethod.method)
        } else {
            null
        }

    private fun isClassHaveRoleAnnotation(handler: Any): Boolean {
        needRoleControllers.forEach {
            if (it.value == handler) return true
        }

        return false
    }

    private fun isMethodHaveRoleAnnotation(method: Method): Boolean {
        method.annotations.forEach {
            if (it.annotationClass == RequireRole::class) return true
        }

        return false
    }

    private fun getRequiredRoleInRoleAnnotation(`object`: Any): Role? {
        `object`::class.annotations.forEach {
            if (it.annotationClass == RequireRole::class) {
                return Role.valueOf((it as RequireRole).role)
            }
        }

        return null
    }
}
