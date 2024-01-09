package com.info.maeumgagym.user.port.out

interface ExistsDeletedUserByIdPort {
    fun existsByIdInNative(oauthId: String): Boolean
}
