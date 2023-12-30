package com.info.maeumgagym.controller.routine.dto.request

import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import java.time.DayOfWeek
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CreateRoutineWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val routineName: String?,

    @field:NotNull(message = "null일 수 없습니다")
    val isArchived: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    val isShared: Boolean?,

    val exerciseInfoModelList: MutableList<ExerciseInfoModel> = ArrayList(),

    val dayOfWeeks: MutableSet<DayOfWeek>?
) {

    fun toRequest() = CreateRoutineRequest(
        routineName!!,
        isArchived!!,
        isShared!!,
        exerciseInfoModelList,
        dayOfWeeks
    )
}
