package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.repository.PickleLikeRepository
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.pickle.model.Pickle
import org.springframework.stereotype.Component

@Component
class PickleMapper(
    private val userMapper: UserMapper,
    private val pickleLikeRepository: PickleLikeRepository
) {

    fun toDomain(pickleJpaEntity: PickleJpaEntity) = Pickle(
        pickleJpaEntity.title,
        pickleJpaEntity.description,
        userMapper.toDomain(pickleJpaEntity.uploader),
        pickleJpaEntity.link,
        pickleJpaEntity.tags,
        pickleJpaEntity.createdAt,
        pickleJpaEntity.likes.size,
        pickleJpaEntity.isDeleted,
        pickleJpaEntity.id
    )

    fun toEntity(pickle: Pickle) = PickleJpaEntity(
        pickle.description,
        pickle.title,
        userMapper.toEntity(pickle.uploader),
        pickle.videoUrl,
        pickle.tags,
        pickle.id?.let { if (pickle.likes != 0) toLikesEntity(it) else null } ?: mutableListOf(),
        pickle.createdAt,
        pickle.id
    )

    private fun toLikesEntity(pickleId: Long) = pickleLikeRepository.findAllByPickleId(pickleId)
}
