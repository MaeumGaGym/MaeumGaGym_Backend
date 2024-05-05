package com.info.maeumgagym.security.mgtoken.impl

import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenRevoker
import com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenContext
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import org.springframework.stereotype.Component

@Component
class MaeumgagymTokenRevokerImpl(
    private val revokedMGTokenContext: RevokedMGTokenContext
) : MaeumgagymTokenRevoker {

    override fun revoke(token: MaeumgagymToken) {
        revokedMGTokenContext.revoke(token)
    }
}
