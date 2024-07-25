package com.info.maeumgagym.core.pose.dto.response

data class PoseRecommendationListResponse(
    val response: List<CategoryMarkedPoseListResponse>
)

data class CategoryMarkedPoseListResponse(
    val category: String,
    val poses: PoseListResponse
)
