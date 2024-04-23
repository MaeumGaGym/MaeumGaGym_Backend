package com.info.maeumgagym.core.pose.port.`in`

import com.info.maeumgagym.core.pose.dto.response.PoseDetailResponse

interface ReadPoseFromIdUseCase {

    fun detailResponseById(id: Long): PoseDetailResponse
}
