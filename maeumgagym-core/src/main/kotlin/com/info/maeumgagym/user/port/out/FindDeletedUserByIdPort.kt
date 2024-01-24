package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User

interface FindDeletedUserByIdPort {

    fun findDeletedUserByOauthId(oauthId: String): User?
}
