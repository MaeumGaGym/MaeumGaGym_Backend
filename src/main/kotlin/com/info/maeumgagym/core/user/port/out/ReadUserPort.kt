package com.info.maeumgagym.core.user.port.out

import com.info.maeumgagym.core.user.model.User
import java.util.*

interface ReadUserPort {

    fun readDeletedByOauthId(oauthId: String): User?

    fun readById(userId: UUID): User?

    fun readByOAuthId(oauthId: String): User?

    fun readByNickname(nickname: String): User?
}
