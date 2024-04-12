package com.info.maeumgagym.pose.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.pose.dto.request.ReadAllPoseRequest
import com.info.maeumgagym.pose.dto.response.PoseListResponse
import com.info.maeumgagym.pose.port.`in`.ReadAllPoseUseCase
import com.info.maeumgagym.pose.port.out.ReadPosePort

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
