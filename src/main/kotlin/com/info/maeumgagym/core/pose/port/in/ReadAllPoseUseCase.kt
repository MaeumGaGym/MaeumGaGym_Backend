package com.info.maeumgagym.core.pose.port.`in`

import com.info.maeumgagym.core.pose.dto.request.ReadAllPoseRequest
import com.info.maeumgagym.core.pose.dto.response.PoseListResponse

interface ReadAllPoseUseCase {

    fun readAll(req: ReadAllPoseRequest): PoseListResponse
}
