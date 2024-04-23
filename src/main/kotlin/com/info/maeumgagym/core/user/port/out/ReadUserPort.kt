package com.info.maeumgagym.core.user.port.out

import com.info.maeumgagym.core.user.model.User
import java.util.*

interface ReadUserPort {

    fun readDeletedByOauthId(oauthId: String): com.info.maeumgagym.core.user.model.User?

    fun readById(userId: UUID): com.info.maeumgagym.core.user.model.User?

    fun readByOAuthId(oauthId: String): com.info.maeumgagym.core.user.model.User?

    fun readByNickname(nickname: String): com.info.maeumgagym.core.user.model.User?
}
