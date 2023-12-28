package com.info.maeumgagym.routine.model

import java.time.DayOfWeek
import java.util.UUID

data class Routine(
    val id: Long? = null,
    val routineName: String,
    val exerciseInfoModelList: MutableList<ExerciseInfoModel>,
    val dayOfWeeks: MutableSet<DayOfWeek>?,
    val routineStatusModel: RoutineStatusModel,
    val userId: UUID
)
