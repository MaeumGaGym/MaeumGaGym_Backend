package com.info.maeumgagym.user.port.out

interface ExistUserByOAuthIdPort {
    fun existByOAuthId(oauthId: String): Boolean
}
