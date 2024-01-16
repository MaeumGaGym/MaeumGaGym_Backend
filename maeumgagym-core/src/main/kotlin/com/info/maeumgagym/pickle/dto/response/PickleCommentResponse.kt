package com.info.maeumgagym.pickle.dto.response

import com.info.maeumgagym.user.dto.response.UserResponse
import java.time.LocalDateTime

class PickleCommentResponse(
    val id: Long,
    val content: String,
    val userResponse: UserResponse,
    val createdAt: LocalDateTime
)
