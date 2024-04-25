package com.info.maeumgagym.application.domain.pickle.mapper

import com.info.maeumgagym.application.domain.pickle.entity.PickleCommentJpaEntity
import com.info.maeumgagym.application.domain.pickle.entity.PickleReplyJpaEntity
import com.info.maeumgagym.application.domain.user.mapper.UserMapper
import com.info.maeumgagym.core.pickle.model.PickleComment
import com.info.maeumgagym.core.pickle.model.PickleReply
import org.springframework.stereotype.Component

@Component
class PickleCommentMapper(
    private val userMapper: UserMapper
) {
    fun toEntity(domain: PickleComment): PickleCommentJpaEntity {
        return domain.run {
            PickleCommentJpaEntity(
                id = id,
                content = content,
                videoId = videoId,
                writer = userMapper.toEntity(writer),
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
                writer = userMapper.toDomain(writer),
                createdAt = createdAt
            )
        }
    }

    fun toEntity(domain: PickleReply): PickleReplyJpaEntity {
        return domain.run {
            PickleReplyJpaEntity(
                id = id,
                content = content,
                videoId = videoId,
                writer = userMapper.toEntity(writer),
                createdAt = createdAt,
                parentComment = toEntity(parentComment)
            )
        }
    }

    fun toDomain(entity: PickleReplyJpaEntity): PickleReply {
        return entity.run {
            PickleReply(
                id = id,
                content = content,
                videoId = videoId,
                writer = userMapper.toDomain(writer),
                createdAt = createdAt,
                parentComment = toDomain(parentComment)
            )
        }
    }
}
