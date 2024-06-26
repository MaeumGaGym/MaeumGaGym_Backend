package com.info.maeumgagym.core.user.port.out

interface ExistUserPort {

    fun existByNicknameOnWithdrawalSafe(nickName: String): Boolean

    fun existsByOAuthId(oauthId: String): Boolean

    fun existByOAuthIdOnWithdrawalSafe(oauthId: String): Boolean
}
