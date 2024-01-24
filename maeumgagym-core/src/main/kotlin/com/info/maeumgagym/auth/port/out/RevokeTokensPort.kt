package com.info.maeumgagym.auth.port.out

interface RevokeTokensPort {

    fun revoke(subject: String)
}
