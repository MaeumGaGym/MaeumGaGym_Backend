package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.pickle.model.PickleLike
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class PickleLikeMapper(
    private val pickleMapper: PickleMapper,
    private val userMapper: UserMapper,
    private val em: EntityManager
) {

    fun toEntity(domain: PickleLike): PickleLikeJpaEntity = domain.run {
        PickleLikeJpaEntity(
            em.merge(pickleMapper.toEntity(pickle)),
            em.merge(userMapper.toEntity(user)),
            isNew
        )
    }

    fun toDomain(entity: PickleLikeJpaEntity): PickleLike = entity.run {
        PickleLike(
            pickleMapper.toDomain(pickle),
            userMapper.toDomain(user),
            isNew
        )
    }
}
