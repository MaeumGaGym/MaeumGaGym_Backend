package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleReplyJpaEntity
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply
import org.springframework.stereotype.Component

@Component
class PickleCommentMapper {
    fun toEntity(pickleComment: PickleComment): PickleCommentJpaEntity {
        return pickleComment.run {
            PickleCommentJpaEntity(
                id = id,
                content = content,
                videoId = videoId,
                writerId = writerId,
                createdAt = createdAt
            )
        }
    }

    fun toDomain(pickleCommentJpaEntity: PickleCommentJpaEntity): PickleComment {
        return pickleCommentJpaEntity.run {
            PickleComment(
                id = id,
                content = content,
                videoId = videoId,
                writerId = writerId,
                createdAt = createdAt
            )
        }
    }

    fun toEntity(pickleReply: PickleReply): PickleReplyJpaEntity {
        return pickleReply.run {
            PickleReplyJpaEntity(
                id = id,
                content = content,
                videoId = videoId,
                writerId = writerId,
                createdAt = createdAt,
                parentComment = parentComment?.let { toEntity(it) }
            )
        }
    }

    fun toDomain(pickleReplyJpaEntity: PickleReplyJpaEntity): PickleReply {
        return pickleReplyJpaEntity.run {
            PickleReply(
                id = id,
                content = content,
                videoId = videoId,
                writerId = writerId,
                createdAt = createdAt,
                parentComment = parentComment?.let { toDomain(it) }
            )
        }
    }
}

