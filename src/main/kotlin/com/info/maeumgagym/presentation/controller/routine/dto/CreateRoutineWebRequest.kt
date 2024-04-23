package com.info.maeumgagym.presentation.controller.routine.dto

import com.info.maeumgagym.core.common.convertor.DayOfWeekConvertor
import com.info.maeumgagym.core.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.core.routine.dto.request.ExerciseInfoRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class CreateRoutineWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    @field:Size(min = 1, max = 12, message = "1글자에서 12글자 사이여야 합니다.")
    val routineName: String?,

    @field:NotNull(message = "null일 수 없습니다")
    val isArchived: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    val isShared: Boolean?,

    @field:NotNull(message = "null일 수 없습니다")
    @field:NotEmpty(message = "비어있을 수 없습니다")
    val exerciseInfoRequestList: MutableList<com.info.maeumgagym.core.routine.dto.request.ExerciseInfoRequest>?,

    @field:NotNull(message = "null일 수 없습니다")
    val dayOfWeeks: MutableSet<String>?
) {

    fun toRequest() = com.info.maeumgagym.core.routine.dto.request.CreateRoutineRequest(
        routineName!!,
        isArchived!!,
        isShared!!,
        exerciseInfoRequestList!!,
        DayOfWeekConvertor.stringToDayOfWeek(dayOfWeeks!!).toMutableSet()
    )
}
