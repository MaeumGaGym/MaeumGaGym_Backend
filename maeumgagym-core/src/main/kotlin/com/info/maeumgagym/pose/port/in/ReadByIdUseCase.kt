package com.info.maeumgagym.pose.port.`in`

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse
import java.util.*

interface ReadByIdUseCase {

    fun poseDetailResponseById(id: UUID): PoseDetailResponse
}
