package com.info.maeumgagym.core.pickle.model

import com.info.maeumgagym.core.pickle.dto.response.PickleCommentResponse
import com.info.maeumgagym.core.user.dto.response.UserResponse
import com.info.maeumgagym.core.user.model.User
import java.time.LocalDateTime

data class PickleComment(
    val id: Long? = null,
    val content: String,
    val videoId: String,
    val writer: User,
    val children: MutableList<PickleReply>? = arrayListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val isDeleted: Boolean = false
) {
    fun toResponse(pickleComment: PickleComment): PickleCommentResponse =
        pickleComment.run {
            PickleCommentResponse(
                id = id!!,
                content = content,
                userResponse = UserResponse(
                    nickname = writer.nickname,
                    profileImage = writer.profileImage
                ),
                createdAt = createdAt
            )
        }
}
