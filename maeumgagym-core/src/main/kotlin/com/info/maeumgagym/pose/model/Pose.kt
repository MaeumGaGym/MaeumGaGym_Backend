package com.info.maeumgagym.pose.model

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse
import java.util.*

data class Pose(

    val id: UUID?,

    val simpleName: String,

    val exactName: String,

    val thumbnail: String,

    val poseImages: MutableList<String>,

    val simplePart: String,

    val exactPart: String,

    val startPose: String,

    val exerciseWay: String,

    val breatheWay: String?,

    val caution: String?
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
