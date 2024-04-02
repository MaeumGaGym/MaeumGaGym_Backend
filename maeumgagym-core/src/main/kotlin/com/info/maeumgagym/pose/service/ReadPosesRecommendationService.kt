package com.info.maeumgagym.pose.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.pose.dto.response.PoseListResponse
import com.info.maeumgagym.pose.port.`in`.ReadPosesRecommendationUseCase
import com.info.maeumgagym.pose.port.out.ReadPosePort

@ReadOnlyUseCase
class ReadPosesRecommendationService(
    private val readPosePort: ReadPosePort
) : ReadPosesRecommendationUseCase {

    override fun readRecommendation(): PoseListResponse {
        val poses = readPosePort.readAllRandomLimit10()

        return PoseListResponse(
            poses.map {
                it.toInfoResponse()
            }
        )
    }
}
