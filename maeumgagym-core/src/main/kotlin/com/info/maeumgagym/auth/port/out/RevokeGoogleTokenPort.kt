package com.info.maeumgagym.auth.port.out

interface RevokeGoogleTokenPort {

    fun revokeGoogleToken(token: String)
}
