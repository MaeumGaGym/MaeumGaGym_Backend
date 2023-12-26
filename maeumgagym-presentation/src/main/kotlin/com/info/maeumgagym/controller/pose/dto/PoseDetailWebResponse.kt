package com.info.maeumgagym.controller.pose.dto

import com.info.maeumgagym.pose.dto.response.PoseDetailResponse

data class PoseDetailWebResponse(

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

    companion object {
        fun toWebResponse(res: PoseDetailResponse) = PoseDetailWebResponse(
            res.simpleName,
            res.exactName,
            res.thumbnail,
            res.poseImages,
            res.simplePart,
            res.exactPart,
            res.startPose,
            res.exerciseWay,
            res.breatheWay,
            res.caution
        )
    }
}
