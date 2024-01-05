package com.info.maeumgagym.controller.routine.dto

import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import java.time.DayOfWeek
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UpdateRoutineWebRequest(
    @field:NotBlank(message = "null일 수 없습니다")
    val routineName: String?,

    @field:NotNull(message = "null일 수 없습니다")
    val isArchived: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    val isShared: Boolean?,

    val exerciseInfoModelList: MutableList<ExerciseInfoModel> = mutableListOf(),

    val dayOfWeeks: MutableSet<DayOfWeek> = mutableSetOf()
) {
    fun toRequest() = UpdateRoutineRequest(
        routineName!!,
        isArchived!!,
        isShared!!,
        exerciseInfoModelList,
        dayOfWeeks
    )
}
