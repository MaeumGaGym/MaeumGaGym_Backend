package com.info.maeumgagym.pose

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
)
