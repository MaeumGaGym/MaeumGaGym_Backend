package com.info.maeumgagym.domain.auth.mapper

import com.info.maeumgagym.auth.model.RefreshToken
import com.info.maeumgagym.domain.auth.entity.RefreshTokenRedisEntity
import org.springframework.stereotype.Component

@Component
class RefreshTokenMapper {

    fun toEntity(domain: RefreshToken) = RefreshTokenRedisEntity(
        domain.subject,
        domain.rfToken,
        domain.ttl
    )

    fun toDomain(entity: RefreshTokenRedisEntity) = RefreshToken(
        entity.subject,
        entity.rfToken,
        entity.ttl
    )
}
