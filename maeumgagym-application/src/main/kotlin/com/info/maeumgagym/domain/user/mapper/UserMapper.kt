package com.info.maeumgagym.domain.user.mapper

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.user.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toEntity(domain: User): UserJpaEntity =
        domain.run {
            UserJpaEntity(
                id = id,
                nickname = nickname,
                oauthId = oauthId,
                roles = roles,
                profileImage = profileImage,
                wakaStartedAt = wakaStartedAt,
                isDeletedAt = isDeletedAt
            )
        }

    fun toDomain(entity: UserJpaEntity): User =
        entity.run {
            User(
                id = id!!,
                nickname = nickname,
                oauthId = oauthId,
                roles = roles,
                profileImage = profileImage,
                wakaStartedAt = wakaStartedAt,
                isDeletedAt = isDeletedAt
            )
        }
}
