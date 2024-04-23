package com.info.maeumgagym.core.pose.service

import com.info.maeumgagym.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.pose.dto.response.PoseListResponse
import com.info.maeumgagym.core.pose.port.`in`.ReadPoseFromTagUseCase
import com.info.maeumgagym.core.pose.port.out.ReadPosePort

@ReadOnlyUseCase
class ReadPoseFromTagService(
    private val readPosePort: ReadPosePort
) : ReadPoseFromTagUseCase {

    override fun readFromTag(tag: String): PoseListResponse {
        val poses = readPosePort.readByTag(tag)

        if (poses.isEmpty()) {
            throw MaeumGaGymException.NO_CONTENT
        }

        return PoseListResponse(
            poses.map {
                it.toInfoResponse()
            }
        )
    }
}
