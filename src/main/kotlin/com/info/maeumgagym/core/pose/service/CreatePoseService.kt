package com.info.maeumgagym.core.pose.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.pose.dto.request.CreatePoseRequest
import com.info.maeumgagym.core.pose.model.Pose
import com.info.maeumgagym.core.pose.port.`in`.CreatePoseUseCase
import com.info.maeumgagym.core.pose.port.out.SavePosePort

@UseCase
internal class CreatePoseService(
    private val savePosePort: SavePosePort
) : CreatePoseUseCase {

    override fun createPose(req: CreatePoseRequest): LocationSubjectDto {
        val pose = req.run {
            savePosePort.save(
                Pose(
                    needMachine = needMachine,
                    category = category.toMutableSet(),
                    simpleName = simpleName,
                    exactName = exactName,
                    thumbnail = thumbnail,
                    video = video,
                    simplePart = simplePart.toMutableSet(),
                    exactPart = exactPart.toMutableSet(),
                    startPose = startPose.toMutableSet(),
                    exerciseWay = exerciseWay.toMutableSet(),
                    breatheWay = breatheWay.toMutableSet(),
                    caution = caution.toMutableSet()
                )
            )
        }

        return LocationSubjectDto(pose.id!!)
    }
}
