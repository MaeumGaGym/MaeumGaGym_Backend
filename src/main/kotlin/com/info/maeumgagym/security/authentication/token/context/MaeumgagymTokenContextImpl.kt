package com.info.maeumgagym.security.authentication.token.context

import com.info.maeumgagym.infrastructure.request.context.RequestContext
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenDecoder
import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class MaeumgagymTokenContextImpl(
    private val requestContext: RequestContext,
    private val maeumgagymTokenDecoder: MaeumgagymTokenDecoder
) : MaeumgagymTokenContext {

    override fun getCurrentToken(): MaeumgagymToken? {
        val req = requestContext.getCurrentRequest()

        val token = req.getHeader(HttpHeaders.AUTHORIZATION)
            ?: return null

        return maeumgagymTokenDecoder.decode(token)
    }
}
