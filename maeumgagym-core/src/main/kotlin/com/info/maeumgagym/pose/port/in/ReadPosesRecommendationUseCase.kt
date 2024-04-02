package com.info.maeumgagym.pose.port.`in`

import com.info.maeumgagym.pose.dto.response.PoseListResponse

interface ReadPosesRecommendationUseCase {

    fun readRecommendation(): PoseListResponse
}
