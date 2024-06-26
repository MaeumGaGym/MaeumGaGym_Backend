package com.info.maeumgagym.application.domain.user.repository

import com.info.maeumgagym.application.domain.user.entity.UserJpaEntity
import org.springframework.data.repository.Repository
import java.util.*

@org.springframework.stereotype.Repository
interface UserRepository : Repository<UserJpaEntity, UUID> {

    fun findByOauthId(oauthId: String): UserJpaEntity?

    fun findById(id: UUID): UserJpaEntity?

    fun findByNickname(nickname: String): UserJpaEntity?

    fun save(entity: UserJpaEntity): UserJpaEntity

    fun deleteById(id: UUID)
}
