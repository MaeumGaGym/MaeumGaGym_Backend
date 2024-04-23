package com.info.maeumgagym.application.domain.pose.entity

import com.info.maeumgagym.application.domain.pose.GetPoseLastModifiedAtImpl
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime

@RedisHash
class PoseLastModifiedAtEntity {
    @Id
    var id: String = GetPoseLastModifiedAtImpl.POSE_LAST_MODIFIED_ENTITY_KEY
        protected set

    var lastModified: LocalDateTime = LocalDateTime.now()
        protected set
}
