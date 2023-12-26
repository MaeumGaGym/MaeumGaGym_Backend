package com.info.maeumgagym.pickle.dto.response

import com.info.maeumgagym.user.dto.response.UserResponse

data class PickleResponse(
    val id: Long,
    val videoUrl: String,
    val title: String,
    val description: String?,
    val tags: List<String>,
    val likeCount: Long,
    val commentCount: Long,
    val userInfo: UserResponse
)
