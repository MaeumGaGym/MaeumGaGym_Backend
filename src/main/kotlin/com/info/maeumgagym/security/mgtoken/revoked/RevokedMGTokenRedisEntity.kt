package com.info.maeumgagym.security.mgtoken.revoked

import com.info.maeumgagym.application.TableNames
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(TableNames.REVOKED_MAEUMGAGYM_TOKEN_TABLE)
class RevokedMGTokenRedisEntity(
    id: String,
    ttl: Long
) {
    @Id
    var id: String = id
        protected set

    @TimeToLive
    var ttl: Long = ttl
        protected set
}
