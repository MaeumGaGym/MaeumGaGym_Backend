package com.info.maeumgagym.infrastructure.request.interceptor

import com.info.maeumgagym.infrastructure.request.context.RequestContext
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestContextInterceptor(
    private val requestContext: RequestContext
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        requestContext.setCurrentRequest(request)

        return true
    }
}
