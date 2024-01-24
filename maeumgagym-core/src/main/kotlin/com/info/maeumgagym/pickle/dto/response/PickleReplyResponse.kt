package com.info.maeumgagym.pickle.dto.response

import com.info.maeumgagym.user.dto.response.UserResponse
import java.time.LocalDateTime

data class PickleReplyResponse(
    val id: Long,
    val parentCommentId: Long,
    val content: String,
    val userResponse: UserResponse,
    val createdAt: LocalDateTime
)
