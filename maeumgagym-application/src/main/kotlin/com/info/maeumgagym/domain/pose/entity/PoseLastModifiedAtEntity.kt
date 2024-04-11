package com.info.maeumgagym.domain.pose.entity

import com.info.maeumgagym.domain.pose.GetPoseLastModifiedAtImpl
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime

@RedisHash
class PoseLastModifiedAtEntity {
    @Id
    val id: String = GetPoseLastModifiedAtImpl.POSE_LAST_MODIFIED_ENTITY_KEY

    val lastModified: LocalDateTime = LocalDateTime.now()
}
