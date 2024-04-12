package com.info.maeumgagym.domain.pose

import com.info.maeumgagym.domain.pose.entity.PoseLastModifiedAtEntity
import com.info.maeumgagym.domain.pose.repository.PoseLastModifiedAtRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetPoseLastModifiedAtImpl(
    private val poseLastModifiedAtRepository: PoseLastModifiedAtRepository
) : GetPoseLastModifiedAt {

    companion object {
        const val POSE_LAST_MODIFIED_ENTITY_KEY = "lastModified"
    }

    override fun invoke(): LocalDateTime {
        var lastModifiedEntity = poseLastModifiedAtRepository.findById(POSE_LAST_MODIFIED_ENTITY_KEY)

        if (lastModifiedEntity == null) {
            lastModifiedEntity = poseLastModifiedAtRepository.save(PoseLastModifiedAtEntity())
        }

        return lastModifiedEntity.lastModified
    }
}
