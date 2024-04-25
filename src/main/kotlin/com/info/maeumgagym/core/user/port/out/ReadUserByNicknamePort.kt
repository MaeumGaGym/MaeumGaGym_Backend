package com.info.maeumgagym.core.user.port.out

import com.info.maeumgagym.core.user.model.User

interface ReadUserByNicknamePort {

    fun readUserByNickname(nickname: String): User?
}
