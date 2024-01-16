package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleReplyJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply
import org.springframework.stereotype.Component

@Component
class PickleCommentMapper(
    private val userMapper: UserMapper
) {
    fun toEntity(pickleComment: PickleComment): PickleCommentJpaEntity {
        return pickleComment.run {
            PickleCommentJpaEntity(
                id = id,
                content = content,
                videoId = videoId,
                writer = userMapper.toEntity(writer),
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
                writer = userMapper.toDomain(writer),
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
                writer = userMapper.toEntity(writer),
                createdAt = createdAt,
                parentComment = toEntity(parentComment)
            )
        }
    }

    fun toDomain(pickleReplyJpaEntity: PickleReplyJpaEntity): PickleReply {
        return pickleReplyJpaEntity.run {
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

