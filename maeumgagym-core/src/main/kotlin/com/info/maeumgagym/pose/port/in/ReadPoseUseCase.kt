package com.info.maeumgagym.pose.port.`in`

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse

interface ReadPoseUseCase {

    fun detailResponseById(id: Long): PoseDetailResponse
}
