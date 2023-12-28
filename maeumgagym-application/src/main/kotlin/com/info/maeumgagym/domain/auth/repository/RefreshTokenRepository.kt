package com.info.maeumgagym.domain.auth.repository

import com.info.maeumgagym.domain.auth.entity.RefreshTokenRedisEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository: CrudRepository<RefreshTokenRedisEntity, String> {

    fun findByRfToken(rfToken: String): RefreshTokenRedisEntity?
}
