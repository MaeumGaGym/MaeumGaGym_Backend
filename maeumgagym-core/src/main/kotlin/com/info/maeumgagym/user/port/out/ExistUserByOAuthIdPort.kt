package com.info.maeumgagym.user.port.out

interface ExistUserByOAuthIdPort {

    fun existUserByOAuthIdOnWithdrawalSafe(oauthId: String): Boolean

    fun existsUserByOAuthId(oauthId: String): Boolean
}
