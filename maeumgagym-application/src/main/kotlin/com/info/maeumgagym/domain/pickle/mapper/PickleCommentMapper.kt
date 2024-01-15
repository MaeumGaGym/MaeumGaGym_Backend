package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleReplyJpaEntity
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply
import org.springframework.stereotype.Component

@Component
class PickleCommentMapper {
    fun toEntity(comment: PickleComment): PickleCommentJpaEntity {
        return comment.run {
            PickleCommentJpaEntity(
                id = id,
                content = content,
                videoId = videoId,
                writerId = writerId,
                createdAt = createdAt
            )
        }
    }

    fun toDomain(entity: PickleCommentJpaEntity): PickleComment {
        return entity.run {
            PickleComment(
                id = id,
                content = content,
                videoId = videoId,
                writerId = writerId,
                createdAt = createdAt
            )
        }
    }

    fun toEntity(reply: PickleReply): PickleReplyJpaEntity {
        return reply.run {
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

    fun toDomain(entity: PickleReplyJpaEntity): PickleReply {
        return entity.run {
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

