package com.info.maeumgagym.user.port.out

interface ExistUserByOAuthIdPort {
    fun existByOAuthIdOfWithdrawalSafe(oauthId: String): Boolean

    fun existsUserByOAuthId(oauthId: String): Boolean
}
