package com.info.maeumgagym.core.pose.port.`in`

import com.info.maeumgagym.pose.dto.response.PoseRecommendationListResponse

interface ReadPosesRecommendationUseCase {

    fun readRecommendation(): PoseRecommendationListResponse
}
