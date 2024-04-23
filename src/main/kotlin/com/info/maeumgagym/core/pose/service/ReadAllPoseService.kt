package com.info.maeumgagym.core.pose.service

import com.info.maeumgagym.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.pose.dto.request.ReadAllPoseRequest
import com.info.maeumgagym.core.pose.dto.response.PoseListResponse
import com.info.maeumgagym.core.pose.port.`in`.ReadAllPoseUseCase
import com.info.maeumgagym.core.pose.port.out.ReadPosePort

@ReadOnlyUseCase
internal class ReadAllPoseService(
    private val readPosePort: ReadPosePort
) : ReadAllPoseUseCase {

    override fun readAll(req: ReadAllPoseRequest): PoseListResponse {
        if (!req.lastUpdated.isBefore(readPosePort.getLastModifiedAt())) {
            throw MaeumGaGymException.NO_CONTENT
        }

        return PoseListResponse(
            readPosePort.readAll().map {
                it.toInfoResponse()
            }
        )
    }
}
