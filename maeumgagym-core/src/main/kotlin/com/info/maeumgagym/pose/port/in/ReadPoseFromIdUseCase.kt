package com.info.maeumgagym.pose.port.`in`

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse

interface ReadPoseFromIdUseCase {

    fun detailResponseById(id: Long): PoseDetailResponse
}
