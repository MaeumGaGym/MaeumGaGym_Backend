package com.info.maeumgagym.core.pose.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.common.dto.LocationSubjectDto
import com.info.maeumgagym.pose.dto.request.CreatePoseRequest
import com.info.maeumgagym.pose.model.Pose
import com.info.maeumgagym.pose.port.`in`.CreatePoseUseCase
import com.info.maeumgagym.pose.port.out.SavePosePort

@UseCase
internal class CreatePoseService(
    private val savePosePort: SavePosePort
) : CreatePoseUseCase {

    override fun createPose(req: CreatePoseRequest): LocationSubjectDto {
        val pose = req.run {
            savePosePort.save(
                Pose(
                    needMachine = needMachine,
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
