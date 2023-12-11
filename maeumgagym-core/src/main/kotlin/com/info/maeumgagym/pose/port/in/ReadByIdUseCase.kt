package com.info.maeumgagym.pose.port.`in`

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse

interface ReadByIdUseCase {

    fun poseDetailResponseById(id: Long): PoseDetailResponse
}
