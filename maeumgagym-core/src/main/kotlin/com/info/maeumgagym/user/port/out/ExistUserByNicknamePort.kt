package com.info.maeumgagym.user.port.out

interface ExistUserByNicknamePort {

    fun existByNicknameOfWithdrawalSafe(nickName: String): Boolean
}
