package com.info.maeumgagym.core.pose.port.`in`

import com.info.maeumgagym.core.common.dto.LocationSubjectDto
import com.info.maeumgagym.pose.dto.request.CreatePoseRequest

interface CreatePoseUseCase {

    fun createPose(req: CreatePoseRequest): LocationSubjectDto
}
