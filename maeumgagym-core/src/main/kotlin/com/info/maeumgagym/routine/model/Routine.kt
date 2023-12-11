package com.info.maeumgagym.routine.model

import java.time.DayOfWeek
import java.util.UUID

data class Routine(
    val id: UUID? = UUID(0, 0),
    val routineName: String,
    val exerciseInfoModelList: MutableList<ExerciseInfoModel>,
    val dayOfWeeks: MutableSet<DayOfWeek>?,
    val routineStatusModel: RoutineStatusModel
)
