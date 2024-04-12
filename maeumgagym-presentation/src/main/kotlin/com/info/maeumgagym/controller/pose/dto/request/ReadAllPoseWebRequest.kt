package com.info.maeumgagym.controller.pose.dto.request

import com.info.maeumgagym.pose.dto.request.ReadAllPoseRequest
import java.time.LocalDateTime

data class ReadAllPoseWebRequest(
    val lastUpdated: LocalDateTime
) {
    fun toRequest(): ReadAllPoseRequest =
        ReadAllPoseRequest(lastUpdated)
}
