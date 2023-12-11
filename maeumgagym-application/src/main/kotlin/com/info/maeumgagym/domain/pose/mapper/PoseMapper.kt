package com.info.maeumgagym.domain.pose.mapper

import com.info.maeumgagym.domain.pose.entity.PoseJpaEntity
import com.info.maeumgagym.pose.Pose
import org.springframework.stereotype.Component

@Component
class PoseMapper {
    fun toEntity(pose: Pose): PoseJpaEntity = pose.run {
        PoseJpaEntity(
            simpleName,
            exactName,
            thumbnail,
            poseImages,
            simplePart,
            exactPart,
            startPose,
            exerciseWay,
            breatheWay,
            caution,
            id
        )
    }

    fun toDomain(poseJpaEntity: PoseJpaEntity): Pose = poseJpaEntity.run {
        Pose(
            id,
            simpleName,
            exactName,
            thumbnail,
            poseImages,
            simplePart,
            exactPart,
            startPose,
            exerciseWay,
            breatheWay,
            caution
        )
    }
}
