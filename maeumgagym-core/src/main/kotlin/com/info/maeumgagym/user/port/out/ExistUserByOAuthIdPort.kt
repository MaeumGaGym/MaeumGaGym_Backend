package com.info.maeumgagym.user.port.out

interface ExistUserByOAuthIdPort {
    fun existUserByOAuthId(oauthId: String): Boolean
}
