package com.info.maeumgagym.infrastructure.request.interceptor

import com.info.maeumgagym.infrastructure.request.context.RequestContext
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * [RequestContext]를 초기화하기 위한 인터셉터
 *
 * @author Daybreak312
 * @since 02-05-2024
 */
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
