package com.info.maeumgagym.user.port.out

interface ExistUserByNicknamePort {

    fun existByNickname(nickName: String): Boolean
}
