package com.info.maeumgagym.global.jwt.repository

import com.info.maeumgagym.global.jwt.entity.AccessTokenRedisEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface AccessTokenRepository : Repository<AccessTokenRedisEntity, String> {

    fun deleteBySubject(subject: String)

    fun save(entity: AccessTokenRedisEntity): AccessTokenRedisEntity

    fun findByAccessToken(accessToken: String): AccessTokenRedisEntity?
}
