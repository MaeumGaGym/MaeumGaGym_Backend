package com.info.maeumgagym.auth.port.out

interface RevokeGoogleTokenPort {

    fun revoke(token: String)
}
