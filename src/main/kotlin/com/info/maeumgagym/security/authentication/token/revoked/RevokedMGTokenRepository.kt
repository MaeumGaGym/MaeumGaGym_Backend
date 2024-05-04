package com.info.maeumgagym.security.authentication.token.revoked

import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface RevokedMGTokenRepository : Repository<RevokedMGTokenRedisEntity, String> {

    fun save(entity: RevokedMGTokenRedisEntity): RevokedMGTokenRedisEntity

    fun findById(id: String): RevokedMGTokenRedisEntity?
}
