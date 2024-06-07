package com.info.maeumgagym.application.domain.pose.mapper

import com.info.maeumgagym.application.domain.pose.entity.PoseJpaEntity
import com.info.maeumgagym.core.pose.model.Pose
import org.springframework.stereotype.Component

@Component
class PoseMapper {
    fun toEntity(pose: Pose): PoseJpaEntity = pose.run {
        PoseJpaEntity(
            needMachine,
            category,
            simpleName,
            exactName,
            thumbnail,
            video,
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
            needMachine,
            category,
            simpleName,
            exactName,
            thumbnail,
            video,
            simplePart,
            exactPart,
            startPose,
            exerciseWay,
            breatheWay,
            caution,
            id
        )
    }
}
