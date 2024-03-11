package com.info.maeumgagym.routine.dto.response

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import com.info.maeumgagym.routine.dto.RoutineStatusDto
import java.util.*

data class RoutineResponse(
    val id: Long,
    val routineName: String,
    val exerciseInfoList: List<ExerciseInfoDto>,
    val dayOfWeeks: List<String>?,
    val routineStatus: RoutineStatusDto,
    val isCompleted: Boolean? = null
)
