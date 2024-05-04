package com.info.maeumgagym.infrastructure.request.context

import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenDecoder
import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class RequestContextImpl(
    private val maeumgagymTokenDecoder: MaeumgagymTokenDecoder
) : RequestContext {

    private val request: ThreadLocal<HttpServletRequest> = ThreadLocal()

    override fun setCurrentRequest(request: HttpServletRequest) {
        this.request.set(request)
    }

    override fun getCurrentRequest(): HttpServletRequest =
        this.request.get()

    override fun getCurrentToken(): MaeumgagymToken? {
        val req = getCurrentRequest()

        val token = req.getHeader(HttpHeaders.AUTHORIZATION)
            ?: return null

        return maeumgagymTokenDecoder.decode(token)
    }
}
