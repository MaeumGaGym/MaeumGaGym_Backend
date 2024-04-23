package com.info.maeumgagym.core.pose.port.`in`

import com.info.maeumgagym.core.pose.dto.response.PoseListResponse

interface ReadPoseFromTagUseCase {

    fun readFromTag(tag: String): PoseListResponse
}
