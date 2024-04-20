package com.info.maeumgagym.security.access.interceptor

import com.info.maeumgagym.security.access.manager.AccessManager
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * [JwtFilter][com.info.maeumgagym.security.jwt.JwtFilter]에서 인가되어 온 [Authentication][com.info.maeumgagym.security.authentication.vo.UserModelAuthentication.user]에 존재하는 [User][com.info.maeumgagym.user.model.User]가 접근하려는 리소스에 대해 허가되어있는지 확인하기 위한 Interceptor
 *
 * AccessManager를 InterceptorChain에 삽입하기 위한 대리자 Interceptor
 *
 * @see AccessManager
 *
 * @author Daybreak312
 * @since 09-04-2024
 */
class AccessManagerDelegateInterceptor(
    private val accessManager: AccessManager
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        accessManager.checkAccessAllowed(request, handler)

        return true
    }
}
