package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import com.info.maeumgagym.pickle.model.PickleComment
import org.springframework.stereotype.Component

@Component
class PickleCommentMapper {
    fun toEntity(pickleComment: PickleComment): PickleCommentJpaEntity {
        val parentCommentEntity = pickleComment.parentComment?.let { toEntity(it) }

        return pickleComment.run {
            PickleCommentJpaEntity(
                content = content,
                pickleId = pickleId,
                writerId = writerId,
                parentComment = parentCommentEntity
            )
        }
    }

    fun toDomain(pickleCommentJpaEntity: PickleCommentJpaEntity): PickleComment {
        val parentComment = pickleCommentJpaEntity.parentComment?.let { toDomain(it) }

        return pickleCommentJpaEntity.run {
            PickleComment(
                content = content,
                pickleId = pickleId,
                writerId = writerId,
                parentComment = parentComment
            )
        }
    }
}

