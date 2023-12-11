package com.info.maeumgagym.pose.model

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse

data class Pose(

    val simpleName: String,

    val exactName: String,

    val thumbnail: String,

    val poseImages: MutableList<String>,

    val simplePart: String,

    val exactPart: String,

    val startPose: String,

    val exerciseWay: String,

    val breatheWay: String?,

    val caution: String?,

    val id: Long?
) {
    fun toDetailResponse() = PoseDetailResponse(
        simpleName,
        exactName,
        thumbnail,
        poseImages,
        simplePart,
        exactPart,
        startPose,
        exerciseWay,
        breatheWay,
        caution
    )
}
