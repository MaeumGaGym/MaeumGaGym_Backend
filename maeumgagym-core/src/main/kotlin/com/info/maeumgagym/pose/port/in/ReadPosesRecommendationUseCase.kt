package com.info.maeumgagym.pose.port.`in`

import com.info.maeumgagym.pose.dto.response.PoseRecommendationListResponse

interface ReadPosesRecommendationUseCase {

    fun readRecommendation(): PoseRecommendationListResponse
}
