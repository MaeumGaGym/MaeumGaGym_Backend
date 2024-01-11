package com.info.maeumgagym.pickle.dto.response

import com.info.maeumgagym.pickle.model.PickleComment
import java.time.LocalDateTime
import java.util.UUID

class PickleCommentResponse(
    val id: Long,
    val content: String,
    val writerId: UUID,
    val childrenComment: MutableList<PickleCommentResponse> = arrayListOf(),
    val createdAt: LocalDateTime?
) {
    companion object {
        fun toDto(pickleComment: PickleComment) =
            pickleComment.run {
                PickleCommentResponse(
                    id = id!!,
                    content = content,
                    writerId = writerId,
                    createdAt = createdAt
                )
            }
    }
}
