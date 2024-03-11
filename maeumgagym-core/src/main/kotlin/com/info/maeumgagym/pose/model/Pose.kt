package com.info.maeumgagym.pose.model

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse

data class Pose(

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

    val caution: MutableSet<String>?,

    val id: Long?
) {
    fun toDetailResponse() = PoseDetailResponse(
        needMachine,
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
