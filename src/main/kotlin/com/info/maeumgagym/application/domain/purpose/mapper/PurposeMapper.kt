package com.info.maeumgagym.application.domain.purpose.mapper

import com.info.maeumgagym.application.domain.purpose.entity.PurposeJpaEntity
import com.info.maeumgagym.application.domain.user.mapper.UserMapper
import com.info.maeumgagym.core.purpose.model.Purpose
import org.springframework.stereotype.Component

@Component
internal class PurposeMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(domain: Purpose): PurposeJpaEntity = domain.run {
        PurposeJpaEntity(
            title = title,
            content = content,
            startDate = startDate,
            endDate = endDate,
            user = userMapper.toEntity(user),
            id = id
        )
    }

    fun toDomain(entity: PurposeJpaEntity): Purpose = entity.run {
        Purpose(
            title = title,
            content = content,
            startDate = startDate,
            endDate = endDate,
            user = userMapper.toDomain(user),
            id = id
        )
    }
}
