package com.info.maeumgagym.user.port.out

interface ExistUserByNicknamePort {

    fun existByNicknameOnWithdrawalSafe(nickName: String): Boolean
}
