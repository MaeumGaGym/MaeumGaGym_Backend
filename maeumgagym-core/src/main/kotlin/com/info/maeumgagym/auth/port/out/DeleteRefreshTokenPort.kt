package com.info.maeumgagym.auth.port.out

interface DeleteRefreshTokenPort {
    fun deleteByOAuthId(oauthId: String)
}
