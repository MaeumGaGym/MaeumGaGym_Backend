package com.info.maeumgagym.core.pose.dto.response

data class PoseInfoResponse(
    val id: Long,
    val category: MutableSet<String>,
    val needMachine: Boolean,
    val name: String,
    val simplePart: List<String>,
    val exactPart: List<String>,
    val thumbnail: String
)
