package com.info.maeumgagym.pose.dto.request

data class CreatePoseRequest(

    val needMachine: Boolean,

    val simpleName: String,

    val exactName: String,

    val thumbnail: String,

    val poseImages: List<String>,

    val simplePart: List<String>,

    val exactPart: List<String>,

    val startPose: List<String>,

    val exerciseWay: List<String>,

    val breatheWay: List<String>,

    val caution: List<String>
)
