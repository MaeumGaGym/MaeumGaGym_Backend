package com.info.maeumgagym.pose.service

import com.info.common.UseCase
import com.info.maeumgagym.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.pose.exception.PoseNotFoundException
import com.info.maeumgagym.pose.port.`in`.ReadByIdUseCase
import com.info.maeumgagym.pose.port.out.FindPoseByUUIDPort
import java.util.UUID

@UseCase
class ReadPoseService(
    private val findPoseByUUIDPort: FindPoseByUUIDPort
) : ReadByIdUseCase {

    override fun poseDetailResponseById(id: UUID): PoseDetailResponse = findPoseByUUIDPort.findById(id)
        ?.toDetailResponse() ?: throw PoseNotFoundException
}
