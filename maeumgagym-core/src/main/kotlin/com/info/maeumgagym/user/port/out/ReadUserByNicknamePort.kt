package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User

interface ReadUserByNicknamePort {

    fun readUserByNickname(nickname: String): User?
}
