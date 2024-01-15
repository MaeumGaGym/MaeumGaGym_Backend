package com.info.maeumgagym.user.model

import java.util.UUID

data class User(
    val id: UUID = UUID(0, 0),
    var nickname: String,
    val roles: MutableList<Role> = mutableListOf(),
    val oauthId: String,
    var profileImage: String? = null,
    var isDeleted: Boolean = false,
    var wakatime: Long = 0
) {

    fun updateWakatime(waka: Long) {
        this.wakatime = waka
    }
}
