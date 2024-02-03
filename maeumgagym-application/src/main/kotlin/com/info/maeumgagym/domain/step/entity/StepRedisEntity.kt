package com.info.maeumgagym.domain.step.entity

import com.info.maeumgagym.TableNames
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = TableNames.REDIS_STEP_TABLE)
class StepRedisEntity(
    id: String,
    numberOfSteps: Int,
    ttl: Long
) {
    @Id
    var id: String = id
        protected set

    @Indexed
    var numberOfSteps: Int = numberOfSteps
        protected set

    @TimeToLive
    var ttl: Long = ttl
        protected set
}
