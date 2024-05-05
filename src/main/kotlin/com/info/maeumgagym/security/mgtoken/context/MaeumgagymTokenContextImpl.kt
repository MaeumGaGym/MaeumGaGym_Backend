package com.info.maeumgagym.security.mgtoken.context

import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenDecoder
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class MaeumgagymTokenContextImpl(
    private val currentRequestContext: CurrentRequestContext,
    private val maeumgagymTokenDecoder: MaeumgagymTokenDecoder
) : MaeumgagymTokenContext {

    override fun getCurrentToken(): MaeumgagymToken? {
        val req = currentRequestContext.getCurrentRequest()

        val token = req.getHeader(HttpHeaders.AUTHORIZATION)
            ?: return null

        return maeumgagymTokenDecoder.decode(token)
    }
}
