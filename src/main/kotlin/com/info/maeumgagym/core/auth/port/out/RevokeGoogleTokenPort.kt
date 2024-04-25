package com.info.maeumgagym.core.auth.port.out

interface RevokeGoogleTokenPort {

    fun revoke(token: String)
}
