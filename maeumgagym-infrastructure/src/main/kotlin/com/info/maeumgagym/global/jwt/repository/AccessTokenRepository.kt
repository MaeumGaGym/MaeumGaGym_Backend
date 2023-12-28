package com.info.maeumgagym.global.jwt.repository

import com.info.maeumgagym.global.jwt.entity.AccessTokenRedisEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessTokenRepository: CrudRepository<AccessTokenRedisEntity, String> {

    fun findByAccessToken(accessToken: String): AccessTokenRedisEntity?
}
