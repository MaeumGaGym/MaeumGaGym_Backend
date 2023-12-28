package com.info.maeumgagym.domain.auth.repository

import com.info.maeumgagym.domain.auth.entity.AccessTokenRedisEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessTokenRepository: CrudRepository<AccessTokenRedisEntity, String> {

    fun existsByAccessToken(accessToken: String): Boolean
}
