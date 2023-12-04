package com.info.maeumgagym.domain.user.mapper

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.user.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toEntity(user: User): UserJpaEntity {
        return UserJpaEntity(
            id = user.id,
            nickname = user.nickname,
            oauthId = user.oauthId,
            roles = user.roles,
            profilePath = user.profilePath,
            isDelete = user.isDeleted
        )
    }

    fun toDomain(userJpaEntity: UserJpaEntity): User {
        return User(
            id = userJpaEntity.id!!,
            nickname = userJpaEntity.nickname,
            oauthId = userJpaEntity.oauthId,
            roles = userJpaEntity.roles,
            profilePath = userJpaEntity.profileImage,
            isDeleted = userJpaEntity.isDeleted
        )
    }
}
