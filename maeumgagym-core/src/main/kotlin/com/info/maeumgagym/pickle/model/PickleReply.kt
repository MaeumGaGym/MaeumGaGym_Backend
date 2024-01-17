package com.info.maeumgagym.pickle.model

import com.info.maeumgagym.pickle.dto.response.PickleReplyResponse
import com.info.maeumgagym.user.dto.response.UserResponse
import com.info.maeumgagym.user.model.User
import java.time.LocalDateTime

data class PickleReply(
    val id: Long? = null,
    val content: String,
    val videoId: String,
    val writer: User,
    val parentComment: PickleComment,
    val createdAt: LocalDateTime? = null,
    val isDeleted: Boolean = false
) {
    fun toResponse(pickleReply: PickleReply) =
        pickleReply.run {
            PickleReplyResponse(
                id = id!!,
                content = content,
                parentCommentId = parentComment.id!!,
                userResponse = UserResponse(nickname = writer.nickname, profileImage = writer.profileImage),
                createdAt = createdAt!!
            )
        }
}
