package com.info.maeumgagym.core.pose.model

import com.info.maeumgagym.core.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.core.pose.dto.response.PoseInfoResponse

data class Pose(

    val needMachine: Boolean,

    val category: MutableSet<String>,

    val simpleName: String,

    val exactName: String,

    val thumbnail: String,

    val video: String,

    val simplePart: MutableSet<String>,

    val exactPart: MutableSet<String>,

    val startPose: MutableSet<String>,

    val exerciseWay: MutableSet<String>,

    val breatheWay: MutableSet<String>?,

    val caution: MutableSet<String>?,

    val id: Long? = null
) {
    fun toDetailResponse() = PoseDetailResponse(
        id = id!!,
        needMachine = needMachine,
        category = category,
        simpleName = simpleName,
        exactName = exactName,
        thumbnail = thumbnail,
        video = video,
        simplePart = simplePart,
        exactPart = exactPart,
        startPose = startPose,
        exerciseWay = exerciseWay,
        breatheWay = breatheWay,
        caution = caution
    )

    fun toInfoResponse() = PoseInfoResponse(
        id = id!!,
        category = category,
        needMachine = needMachine,
        name = exactName,
        simplePart = simplePart.toList(),
        exactPart = exactPart.toList(),
        thumbnail = thumbnail
    )
}
