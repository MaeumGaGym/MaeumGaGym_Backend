package com.info.maeumgagym.global.jwt.repository

import com.info.maeumgagym.global.jwt.entity.RefreshTokenRedisEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository: CrudRepository<RefreshTokenRedisEntity, String> {

    fun findByRfToken(rfToken: String): RefreshTokenRedisEntity?
}
