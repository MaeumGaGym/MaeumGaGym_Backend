package com.info.maeumgagym.security.authentication.token.impl

import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenRevoker
import com.info.maeumgagym.security.authentication.token.revoked.RevokedMGTokenContext
import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken
import org.springframework.stereotype.Component

@Component
class MaeumgagymTokenRevokerImpl(
    private val revokedMGTokenContext: RevokedMGTokenContext
) : MaeumgagymTokenRevoker {

    override fun revoke(token: MaeumgagymToken) {
        revokedMGTokenContext.revoke(token)
    }
}
