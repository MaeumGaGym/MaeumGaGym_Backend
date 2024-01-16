package com.info.maeumgagym.pickle.model

import com.info.maeumgagym.pickle.dto.response.PickleCommentResponse
import com.info.maeumgagym.user.dto.response.UserResponse
import java.time.LocalDateTime
import java.util.UUID

data class PickleComment(
    val id: Long? = null,
    val content: String,
    val videoId: String,
    val writerId: UUID,
    val children: MutableList<PickleReply>? = arrayListOf(),
    val createdAt: LocalDateTime,
    val isDeleted: Boolean = false
) {
    companion object {
        fun toResponse(pickleComment: PickleComment, userResponse: UserResponse) =
            pickleComment.run {
                PickleCommentResponse(
                    id = id!!,
                    content = content,
                    userResponse = userResponse,
                    createdAt = createdAt
                )
            }
    }
}
