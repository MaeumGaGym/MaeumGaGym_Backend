package com.info.maeumgagym.domain.pickle.mapper

import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.pickle.model.PickleLike
import org.springframework.stereotype.Component

@Component
class PickleLikeMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(domain: PickleLike): PickleLikeJpaEntity =
        PickleLikeJpaEntity(
            domain.pickleId,
            userMapper.toEntity(domain.user)
        )

    fun toDomain(entity: PickleLikeJpaEntity): PickleLike =
        PickleLike(
            entity.pickleId,
            userMapper.toDomain(entity.user)
        )
}
