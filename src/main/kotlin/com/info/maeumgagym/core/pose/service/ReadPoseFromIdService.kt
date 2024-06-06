package com.info.maeumgagym.core.pose.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.core.pose.port.`in`.ReadPoseFromIdUseCase
import com.info.maeumgagym.core.pose.port.out.ReadPosePort

@ReadOnlyUseCase
internal class ReadPoseFromIdService(
    private val readPosePort: ReadPosePort
) : ReadPoseFromIdUseCase {

    override fun detailResponseById(id: Long): PoseDetailResponse =
        // (pose.id = id)라면 -> response, else -> 예외 처리
        readPosePort.readById(id)?.toDetailResponse() ?: throw BusinessLogicException.POSE_NOT_FOUND
}
