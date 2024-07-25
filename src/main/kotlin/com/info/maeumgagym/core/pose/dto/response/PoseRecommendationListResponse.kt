package com.info.maeumgagym.core.pose.dto.response

data class PoseRecommendationListResponse(
    val responses: List<CategoryMarkedPoseListResponse>
)

data class CategoryMarkedPoseListResponse(
    val category: String,
    val poses: List<PoseInfoResponse>
)
