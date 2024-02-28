package com.info.maeumgagym.pose.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.pose.exception.PoseNotFoundException
import com.info.maeumgagym.pose.port.`in`.ReadPoseUseCase
import com.info.maeumgagym.pose.port.out.ReadPosePort

@ReadOnlyUseCase
internal class ReadPoseService(
    private val readPosePort: ReadPosePort
) : ReadPoseUseCase {

    override fun detailResponseById(id: Long): PoseDetailResponse =
        // (pose.id = id)라면 -> response, else -> 예외 처리
        readPosePort.readById(id)?.toDetailResponse() ?: throw PoseNotFoundException
}
