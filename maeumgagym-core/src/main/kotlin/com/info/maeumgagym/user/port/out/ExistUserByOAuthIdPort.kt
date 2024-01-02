package com.info.maeumgagym.user.port.out

interface ExistUserByOAuthIdPort {
    fun existByOAuthIdInNative(oauthId: String): Boolean

    fun existsUserByOAuthId(oauthId: String): Boolean
}
