package com.info.maeumgagym.pose.dto.response

data class PoseDetailResponse(

    val needMachine: Boolean,

    val simpleName: String,

    val exactName: String,

    val thumbnail: String,

    val poseImages: MutableSet<String>,

    val simplePart: MutableSet<String>,

    val exactPart: MutableSet<String>,

    val startPose: MutableSet<String>,

    val exerciseWay: MutableSet<String>,

    val breatheWay: MutableSet<String>?,

    val caution: MutableSet<String>?
)
