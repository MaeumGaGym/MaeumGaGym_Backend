package com.info.maeumgagym.controller.routine.dto

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
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

    @field:NotNull(message = "null일 수 없습니다")
    @field:NotEmpty(message = "비어있을 수 없습니다")
    val exerciseInfoDtoList: MutableList<ExerciseInfoDto>?,

    val dayOfWeeks: MutableSet<DayOfWeek>?
) {
    fun toRequest() = UpdateRoutineRequest(
        routineName!!,
        isArchived!!,
        isShared!!,
        exerciseInfoDtoList!!,
        dayOfWeeks
    )
}
