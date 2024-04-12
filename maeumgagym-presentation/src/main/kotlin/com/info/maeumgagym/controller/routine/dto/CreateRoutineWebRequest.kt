package com.info.maeumgagym.controller.routine.dto

import com.info.maeumgagym.common.convertor.DayOfWeekConvertor
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.dto.request.ExerciseInfoRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateRoutineWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val routineName: String?,

    @field:NotNull(message = "null일 수 없습니다")
    val isArchived: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    val isShared: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    @field:NotEmpty(message = "비어있을 수 없습니다")
    val exerciseInfoRequestList: MutableList<ExerciseInfoRequest>?,

    @field:NotNull(message = "null일 수 없습니다")
    val dayOfWeeks: MutableSet<String>?
) {

    fun toRequest() = CreateRoutineRequest(
        routineName!!,
        isArchived!!,
        isShared!!,
        exerciseInfoRequestList!!,
        dayOfWeeks!!.map {
            DayOfWeekConvertor.stringToDayOfWeek(it)
        }.toMutableSet()
    )
}
