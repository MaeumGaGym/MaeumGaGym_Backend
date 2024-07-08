package com.info.maeumgagym.security.mgtoken.revoked

import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface UsableMGTokenRepository : Repository<UsableMGTokenRedisEntity, String> {

    fun save(entity: UsableMGTokenRedisEntity): UsableMGTokenRedisEntity

    fun deleteById(id: String)

    fun findById(id: String): UsableMGTokenRedisEntity?
}
