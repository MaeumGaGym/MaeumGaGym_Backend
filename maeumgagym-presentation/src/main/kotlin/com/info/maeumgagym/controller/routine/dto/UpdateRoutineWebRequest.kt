package com.info.maeumgagym.controller.routine.dto

import com.info.maeumgagym.routine.dto.request.ExerciseInfoRequest
import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import java.time.DayOfWeek
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UpdateRoutineWebRequest(
    @field:NotBlank(message = "null일 수 없습니다")
    val routineName: String?,

    @field:NotNull(message = "null일 수 없습니다")
    val isArchived: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    val isShared: Boolean?,

    val dayOfWeeks: MutableSet<DayOfWeek>?,

    @field:NotNull(message = "null일 수 없습니다")
    @field:NotEmpty(message = "비어있을 수 없습니다")
    val exerciseInfoRequestList: MutableList<ExerciseInfoRequest>?
) {
    fun toRequest() = UpdateRoutineRequest(
        routineName!!,
        isArchived!!,
        isShared!!,
        exerciseInfoRequestList!!,
        dayOfWeeks
    )
}
