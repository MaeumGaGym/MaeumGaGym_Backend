package com.info.maeumgagym.fileserver.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

@RedisHash
class VideoIdAndUploaderIdRedisEntity(
    videoId: String,
    uploaderId: UUID,
    ttl: Long
) {

    @Id
    var videoId: String = videoId
        protected set

    var uploaderId: UUID = uploaderId
        protected set

    @TimeToLive
    var ttl: Long = ttl
        protected set
}
