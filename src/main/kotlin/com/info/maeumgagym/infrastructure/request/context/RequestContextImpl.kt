package com.info.maeumgagym.infrastructure.request.context

import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class RequestContextImpl : RequestContext {

    private val request: ThreadLocal<HttpServletRequest> = ThreadLocal()

    override fun setCurrentRequest(request: HttpServletRequest) {
        this.request.set(request)
    }

    override fun getCurrentRequest(): HttpServletRequest =
        this.request.get()
}
