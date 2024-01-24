package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User
import java.util.*

interface ReadUserPort {

    fun readDeletedByOauthId(oauthId: String): User?

    fun readById(userId: UUID): User?

    fun readByOAuthId(oauthId: String): User?
}
