package com.info.maeumgagym.core.user.dto.response

data class UserProfileResponse(
    val nickname: String,
    val profileImage: String?,
    val level: Int,
    val totalWakatime: Long
)
