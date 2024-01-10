package com.info.maeumgagym.user.port.out

interface ExistUserByNicknamePort {

    fun existByNicknameInNative(nickName: String): Boolean
}
