package com.info.maeumgagym.pose

import java.util.*

data class Pose(

    val id: UUID?,

    val name: String,

    val exactName: String,

    val thumbnail: String,

    val poseImages: MutableList<String>,

    val exactPart: String,

    val easyPart: String,

    val startPose: String,

    val exerciseWay: String,

    val breatheWay: String?,

    val caution: String
)
