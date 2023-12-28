package com.info.maeumgagym.domain.auth.entity

import com.info.maeumgagym.TableNames
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = TableNames.REFRESH_TOKEN_TABLE)
class RefreshTokenRedisEntity (
    subject: String,
    rfToken: String,
    ttl: Long
) {
    @Id
    var subject: String = subject
        protected set

    @Indexed
    var rfToken: String = rfToken
        protected set

    @TimeToLive
    var ttl: Long = ttl
        protected set
}
