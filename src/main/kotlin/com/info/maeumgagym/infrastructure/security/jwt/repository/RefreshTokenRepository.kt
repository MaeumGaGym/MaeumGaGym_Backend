package com.info.maeumgagym.infrastructure.security.jwt.repository

import com.info.maeumgagym.infrastructure.security.jwt.entity.RefreshTokenRedisEntity
import org.springframework.data.repository.Repository

/**
 * Refresh Token을 저장하는 Redis의 DAO Repository
 *
 * @see AccessTokenRepository
 *
 * @author gurdl0525
 * @since 28-12-2023
 */
@org.springframework.stereotype.Repository
interface RefreshTokenRepository : Repository<RefreshTokenRedisEntity, String> {

    fun findById(id: String): RefreshTokenRedisEntity?

    fun deleteById(id: String)

    fun save(entity: RefreshTokenRedisEntity): RefreshTokenRedisEntity

    fun findByRfToken(rfToken: String): RefreshTokenRedisEntity?
}
