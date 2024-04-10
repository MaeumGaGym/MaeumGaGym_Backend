package com.info.maeumgagym.security.filter

import com.info.common.security.NeedRole
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.collection.AnnotationBeanCollection
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.user.model.Role
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.HandlerMapping
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
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val dispatcherServlet: DispatcherServlet
) : HandlerInterceptor {

    private lateinit var needRoleControllers: Map<String, Any>

    private lateinit var handlerMappings: List<HandlerMapping>

    private var initialed: Boolean = false

    private fun initialize() {
        handlerMappings = dispatcherServlet.handlerMappings!!.toList()
        needRoleControllers = annotationBeanCollection.getBeans(NeedRole::class as KClass<Annotation>)
        initialed = true
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (!initialed) initialize()

        val webAdapter = castHandlerToWebAdapterOrNull(handler)

        if (needRoleAuthentication(webAdapter)) {
            val currentUser = readCurrentUserPort.readCurrentUser()

            val roleNeed = getRoleNeed(webAdapter!!)
                ?: throw NullPointerException("Role authentication required BUT \"need role\" was null")

            if (!currentUser.roles.contains(roleNeed)) {
                throw AuthenticationException.ROLE_REQUIRED
            }
        }

        return true
    }

    private fun castHandlerToWebAdapterOrNull(handler: Any): Any? {
        if (handler !is HandlerMethod) {
            return null
        }

        return handler.bean
    }

    private fun needRoleAuthentication(handler: Any?): Boolean {
        if (handler == null) {
            return false
        }

        return haveNeedRoleAnnotation(handler)
    }

    private fun getRoleNeed(handler: Any): Role? {
        handler::class.annotations.forEach {
            if (it.annotationClass == NeedRole::class) {
                return Role.valueOf((it as NeedRole).role)
            }
        }

        return null
    }

    private fun haveNeedRoleAnnotation(handler: Any): Boolean {
        needRoleControllers.forEach {
            if (it.value == handler) return true
        }
        return false
    }
    /* `object`::class.annotations.map { it.annotationClass }.contains(NeedRole::class)*/

}
