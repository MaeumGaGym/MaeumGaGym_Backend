package com.info.maeumgagym.core.pose.port.`in`

import com.info.maeumgagym.core.pose.dto.response.PoseListResponse
import java.time.LocalDateTime

interface ReadAllPoseUseCase {

    fun readAll(lastUpdated: LocalDateTime?): PoseListResponse
}
