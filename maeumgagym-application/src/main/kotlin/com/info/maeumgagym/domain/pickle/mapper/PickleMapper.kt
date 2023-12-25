package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.model.PickleLike
import org.springframework.stereotype.Component

@Component
class PickleMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(pickle: Pickle) = pickle.run {
        PickleJpaEntity(
            description = description,
            title = title,
            uploader = userMapper.toEntity(uploader),
            videoUrl = videoUrl,
            likeCount = likeCount,
            tags = tags,
            createdAt = createdAt,
            id = id
        )
    }

    fun toDomain(pickleJpaEntity: PickleJpaEntity) = pickleJpaEntity.run {
        Pickle(
            title = title,
            description = description,
            uploader = userMapper.toDomain(uploader),
            videoUrl = videoUrl,
            likeCount = likeCount,
            tags = tags,
            createdAt = createdAt,
            isDeleted = isDeleted,
            id = id
        )
    }

    fun toEntityLike(pickleLike: PickleLike): PickleLikeJpaEntity =
        PickleLikeJpaEntity(
            pickle = toEntity(pickleLike.pickle),
            user = userMapper.toEntity(pickleLike.user)
        )

    fun toDomainLike(pickleLikeJpaEntity: PickleLikeJpaEntity): PickleLike =
        PickleLike(
            pickle = toDomain(pickleLikeJpaEntity.pickle),
            user = userMapper.toDomain(pickleLikeJpaEntity.user)
        )
}
