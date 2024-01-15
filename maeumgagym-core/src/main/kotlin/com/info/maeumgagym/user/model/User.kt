package com.info.maeumgagym.user.model

import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class User(
    val id: UUID = UUID(0, 0),
    var nickname: String,
    val roles: MutableList<Role> = mutableListOf(),
    val oauthId: String,
    var profileImage: String? = null,
    var isDeleted: Boolean = false,
    var wakatime: LocalTime = LocalTime.of(0, 0, 0)
) {

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun restoreUser() {
        this.isDeleted = false
    }

    fun updateProfile(profileImage: String) {
        this.profileImage = profileImage
    }

    fun updateWakatime(waka: LocalTime) {
        this.wakatime = waka
    }
}
