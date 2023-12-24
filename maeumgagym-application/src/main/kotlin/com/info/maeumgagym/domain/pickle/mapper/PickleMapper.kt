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

    fun toEntity(pickle: Pickle): PickleJpaEntity =
        PickleJpaEntity(
            description = pickle.description,
            title = pickle.title,
            uploader = userMapper.toEntity(pickle.uploader),
            videoUrl = pickle.videoUrl,
            likeCount = pickle.likeCount,
            tags = pickle.tags,
            createdAt = pickle.createdAt,
            id = pickle.id
        )

    fun toDomain(pickleJpaEntity: PickleJpaEntity): Pickle =
        Pickle(
            title = pickleJpaEntity.title,
            description = pickleJpaEntity.description,
            uploader = userMapper.toDomain(pickleJpaEntity.uploader),
            videoUrl = pickleJpaEntity.videoUrl,
            likeCount = pickleJpaEntity.likeCount,
            tags = pickleJpaEntity.tags,
            createdAt = pickleJpaEntity.createdAt,
            isDeleted = pickleJpaEntity.isDeleted,
            id = pickleJpaEntity.id
        )

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
