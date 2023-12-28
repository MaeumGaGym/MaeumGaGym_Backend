package com.info.maeumgagym.domain.auth.mapper

import com.info.maeumgagym.auth.model.AccessToken
import com.info.maeumgagym.domain.auth.entity.AccessTokenRedisEntity
import org.springframework.stereotype.Component

@Component
class AccessTokenMapper {

    fun toEntity(domain: AccessToken) = AccessTokenRedisEntity(
        domain.subject,
        domain.accessToken,
        domain.ttl
    )

    fun toDomain(entity: AccessTokenRedisEntity) = AccessToken(
        entity.subject,
        entity.accessToken,
        entity.ttl
    )
}
