package com.info.maeumgagym.security.mgtoken.revoked

import com.info.maeumgagym.application.TableNames
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

/**
 * 토큰, 정확히는 [tokenId][com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken.tokenId]의 무효화 정보를 저장하기 위한 Entity
 *
 * @author Daybreak312
 * @since 04-05-2024
 */
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
