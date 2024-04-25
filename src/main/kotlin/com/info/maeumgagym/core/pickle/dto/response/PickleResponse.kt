package com.info.maeumgagym.core.pickle.dto.response

import com.info.maeumgagym.core.user.dto.response.UserResponse

data class PickleResponse(
    val videoId: String,
    val videoURL: String,
    val title: String,
    val description: String?,
    val tags: List<String>,
    val likeCount: Long,
    val commentCount: Long,
    val userInfo: UserResponse
)
