package com.info.maeumgagym.security.jwt.repository

import com.info.maeumgagym.security.jwt.entity.RefreshTokenRedisEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface RefreshTokenRepository : Repository<RefreshTokenRedisEntity, String> {

    fun findById(id: String): RefreshTokenRedisEntity?

    fun deleteById(id: String)

    fun save(entity: RefreshTokenRedisEntity): RefreshTokenRedisEntity

    fun findByRfToken(rfToken: String): RefreshTokenRedisEntity?
}
