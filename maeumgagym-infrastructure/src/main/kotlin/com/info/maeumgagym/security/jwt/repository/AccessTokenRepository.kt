package com.info.maeumgagym.security.jwt.repository

import com.info.maeumgagym.security.jwt.entity.AccessTokenRedisEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface AccessTokenRepository : Repository<AccessTokenRedisEntity, String> {

    fun findById(id: String): AccessTokenRedisEntity?

    fun deleteById(id: String)

    fun save(entity: AccessTokenRedisEntity): AccessTokenRedisEntity

    fun findByAccessToken(accessToken: String): AccessTokenRedisEntity?
}
