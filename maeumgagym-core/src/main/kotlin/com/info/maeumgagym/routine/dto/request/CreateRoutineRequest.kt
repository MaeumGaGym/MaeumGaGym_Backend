package com.info.maeumgagym.routine.dto.request

import java.time.DayOfWeek

class CreateRoutineRequest(
    val routineName: String,
    val isArchived: Boolean,
    val isShared: Boolean,
    val exerciseInfoResponseList: MutableList<ExerciseInfoRequest>,
    val dayOfWeeks: MutableSet<DayOfWeek>?
)
