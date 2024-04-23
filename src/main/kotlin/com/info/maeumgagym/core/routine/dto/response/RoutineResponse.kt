package com.info.maeumgagym.core.routine.dto.response

import com.info.maeumgagym.core.routine.dto.RoutineStatusDto

data class RoutineResponse(
    val id: Long,
    val routineName: String,
    val exerciseInfoResponseList: List<ExerciseInfoResponse>,
    val dayOfWeeks: List<String>?,
    val routineStatus: RoutineStatusDto,
    val isCompleted: Boolean? = null
)
