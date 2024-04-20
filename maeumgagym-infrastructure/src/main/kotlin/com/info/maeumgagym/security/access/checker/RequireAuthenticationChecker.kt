package com.info.maeumgagym.security.access.checker

import com.info.common.security.RequireAuthentication
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import javax.servlet.http.HttpServletRequest

/**
 * [@RequireAuthentication][RequireAuthentication]이 부착된 Handler에 대한 접근 허가 확인자
 *
 * @author Daybreak312
 * @since 21-04-2024
 */
@Component
class RequireAuthenticationChecker : AbstractAccessAllowedChecker() {

    override fun check(request: HttpServletRequest, handler: HandlerMethod) {
        handler.getAnnotationOrNull(RequireAuthentication::class) ?: return

        checkInvalidAuthentication()
    }
}
