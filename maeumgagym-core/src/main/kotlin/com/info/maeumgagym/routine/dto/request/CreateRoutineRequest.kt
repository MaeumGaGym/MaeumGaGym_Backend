package com.info.maeumgagym.routine.dto.request

import java.time.DayOfWeek

class CreateRoutineRequest(
    val routineName: String,
    val isArchived: Boolean,
    val isShared: Boolean,
    val exerciseInfoRequestList: MutableList<ExerciseInfoRequest>,
    val dayOfWeeks: MutableSet<DayOfWeek>?
)
