package com.info.maeumgagym.pose.service

import com.info.common.UseCase
import com.info.maeumgagym.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.pose.exception.PoseNotFoundException
import com.info.maeumgagym.pose.port.`in`.ReadByIdUseCase
import com.info.maeumgagym.pose.port.out.FindPoseByIdPort

@UseCase
class ReadPoseService(
    private val findPoseByIdPort: FindPoseByIdPort
) : ReadByIdUseCase {

    override fun poseDetailResponseById(id: Long): PoseDetailResponse =
        findPoseByIdPort.findById(id)?.toDetailResponse() ?: throw PoseNotFoundException
}
