package com.info.maeumgagym.pose.port.`in`

import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.pose.dto.request.CreatePoseRequest

interface CreatePoseUseCase {

    fun createPose(req: CreatePoseRequest): LocationSubjectDto
}
