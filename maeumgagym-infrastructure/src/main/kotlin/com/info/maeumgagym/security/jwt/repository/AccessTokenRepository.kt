package com.info.maeumgagym.security.jwt.repository

import com.info.maeumgagym.security.jwt.entity.AccessTokenRedisEntity
import org.springframework.data.repository.Repository

/**
 * Access Token을 저장하는 Redis의 DAO Repository
 *
 * @see RefreshTokenRepository
 *
 * @author gurdl0525
 * @since 28-12-2023
 */
@org.springframework.stereotype.Repository
interface AccessTokenRepository : Repository<AccessTokenRedisEntity, String> {

    fun findById(id: String): AccessTokenRedisEntity?

    fun deleteById(id: String)

    fun save(entity: AccessTokenRedisEntity): AccessTokenRedisEntity

    fun findByAccessToken(accessToken: String): AccessTokenRedisEntity?
}
