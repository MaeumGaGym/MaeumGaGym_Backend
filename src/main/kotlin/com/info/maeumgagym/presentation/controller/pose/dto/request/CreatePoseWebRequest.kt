package com.info.maeumgagym.presentation.controller.pose.dto.request

import com.info.maeumgagym.core.pose.dto.request.CreatePoseRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreatePoseWebRequest(

    @field:NotNull(message = "null일 수 없습니다")
    val needMachine: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    val category: List<String>?,

    @field:NotBlank(message = "null일 수 없습니다")
    val simpleName: String?,

    @field:NotBlank(message = "null일 수 없습니다")
    val exactName: String?,

    @field:NotBlank(message = "null일 수 없습니다")
    val thumbnail: String?,

    @field:NotNull(message = "null일 수 없습니다")
    val video: String?,

    @field:NotNull(message = "null일 수 없습니다")
    val simplePart: List<String>?,

    @field:NotNull(message = "null일 수 없습니다")
    val exactPart: List<String>?,

    @field:NotNull(message = "null일 수 없습니다")
    val startPose: List<String>?,

    @field:NotNull(message = "null일 수 없습니다")
    val exerciseWay: List<String>?,

    @field:NotNull(message = "null일 수 없습니다")
    val breatheWay: List<String>?,

    @field:NotNull(message = "null일 수 없습니다")
    val caution: List<String>?
) {
    fun toRequest(): CreatePoseRequest = CreatePoseRequest(
        needMachine = needMachine!!,
        category = category!!,
        simpleName = simpleName!!,
        exactName = exactName!!,
        thumbnail = thumbnail!!,
        video = video!!,
        simplePart = simplePart!!,
        exactPart = exactPart!!,
        startPose = startPose!!,
        exerciseWay = exerciseWay!!,
        breatheWay = breatheWay!!,
        caution = caution!!
    )
}
