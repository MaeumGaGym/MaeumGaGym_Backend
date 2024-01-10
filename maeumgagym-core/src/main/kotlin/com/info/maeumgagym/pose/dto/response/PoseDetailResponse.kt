package com.info.maeumgagym.pose.dto.response

data class PoseDetailResponse(

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
)
