package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.pickle.model.Pickle
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class PickleMapper(
    private val pickleLikeMapper: PickleLikeMapper,
    private val userMapper: UserMapper,
    private val em: EntityManager
) {

    fun toEntity(pickle: Pickle) = pickle.run {
        PickleJpaEntity(
            videoId = videoId,
            description = description,
            title = title,
            uploader = userMapper.toEntity(uploader),
            likes = em.find(PickleJpaEntity::class.java, videoId)?.likes ?: mutableSetOf(),
            likeCount = likeCount,
            tags = tags,
            createdAt = createdAt
        )
    }

    fun toDomain(pickleJpaEntity: PickleJpaEntity) = pickleJpaEntity.run {
        Pickle(
            videoId = videoId,
            title = title,
            description = description,
            uploader = userMapper.toDomain(uploader),
            likes = likes.map { pickleLikeMapper.toDomain(it) }.toMutableSet(),
            likeCount = likeCount,
            tags = tags,
            createdAt = createdAt,
            isDeleted = isDeleted
        )
    }
}
