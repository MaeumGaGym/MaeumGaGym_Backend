package com.info.maeumgagym.application.domain.pickle.mapper

import com.info.maeumgagym.application.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.application.domain.pickle.entity.PickleLikeJpaEntity
import com.info.maeumgagym.application.domain.user.mapper.UserMapper
import com.info.maeumgagym.core.pickle.model.PickleLike
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class PickleLikeMapper(
    private val userMapper: UserMapper,
    private val em: EntityManager
) {

    fun toEntity(domain: PickleLike): PickleLikeJpaEntity = domain.run {
        PickleLikeJpaEntity(
            pickle = pickle.run {
                PickleJpaEntity(
                    videoId = videoId,
                    description = description,
                    title = title,
                    uploader = em.merge(userMapper.toEntity(uploader)),
                    likes = em.find(PickleJpaEntity::class.java, pickle.videoId).likes,
                    likeCount = likeCount,
                    tags = tags,
                    createdAt = createdAt
                )
            },
            user = em.merge(userMapper.toEntity(user)),
            id = id
        )
    }

    fun toDomain(entity: PickleLikeJpaEntity): PickleLike = entity.run {
        PickleLike(
            pickle = pickle.run {
                com.info.maeumgagym.core.pickle.model.Pickle(
                    videoId = videoId,
                    description = description,
                    title = title,
                    uploader = userMapper.toDomain(uploader),
                    likes = mutableSetOf(),
                    likeCount = likeCount,
                    tags = tags,
                    createdAt = createdAt
                )
            },
            user = userMapper.toDomain(user),
            id = id
        )
    }
}
