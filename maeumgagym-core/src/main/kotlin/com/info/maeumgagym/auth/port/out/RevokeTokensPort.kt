package com.info.maeumgagym.auth.port.out

interface RevokeTokensPort {

    fun revokeTokens(subject: String)
}
