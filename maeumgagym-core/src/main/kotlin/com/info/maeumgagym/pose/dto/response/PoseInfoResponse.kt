package com.info.maeumgagym.pose.dto.response

data class PoseInfoResponse(
    val id: Long,
    val needMachine: Boolean,
    val name: String,
    val simplePart: List<String>,
    val exactPart: List<String>,
    val thumbnail: String
)
