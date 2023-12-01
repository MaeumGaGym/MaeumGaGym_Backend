package com.info.maeumgagym.domain.user.repository

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserJpaEntity, UUID> {

    fun findByOauthId(oauthId: String): UserJpaEntity?
}
