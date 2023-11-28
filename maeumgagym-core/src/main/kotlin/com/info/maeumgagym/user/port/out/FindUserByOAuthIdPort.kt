package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User

interface FindUserByOAuthIdPort {
    fun findUserByOAuthId(oauthId: String): User?
}
