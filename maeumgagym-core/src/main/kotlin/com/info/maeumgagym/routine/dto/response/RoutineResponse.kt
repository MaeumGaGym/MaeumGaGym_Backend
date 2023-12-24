package com.info.maeumgagym.routine.dto.response

import com.info.maeumgagym.routine.model.ExerciseInfoModel
import com.info.maeumgagym.routine.model.RoutineStatusModel
import java.util.*

data class RoutineResponse(
    val id: UUID,
    val routineName: String,
    val exerciseInfoList: MutableList<ExerciseInfoModel>,
    val dayOfWeeks: List<String>?,
    val routineStatus: RoutineStatusModel
)
