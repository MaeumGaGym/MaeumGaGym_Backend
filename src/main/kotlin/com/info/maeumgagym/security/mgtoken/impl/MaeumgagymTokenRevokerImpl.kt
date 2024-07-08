package com.info.maeumgagym.security.mgtoken.impl

import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenRevoker
import com.info.maeumgagym.security.mgtoken.revoked.UsableMGTokenContext
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import org.springframework.stereotype.Component

@Component
class MaeumgagymTokenRevokerImpl(
    private val usableMGTokenContext: UsableMGTokenContext
) : MaeumgagymTokenRevoker {

    override fun revoke(token: MaeumgagymToken) {
        usableMGTokenContext.revoke(token)
    }
}
