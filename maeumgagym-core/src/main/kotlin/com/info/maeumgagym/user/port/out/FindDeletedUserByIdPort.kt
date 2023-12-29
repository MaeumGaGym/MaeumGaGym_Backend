package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User

interface FindDeletedUserByIdPort {

    fun findByIdOrNullInNative(oauthId: String): User?
}
