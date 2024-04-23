package com.info.maeumgagym.core.routine.dto.request

import java.time.DayOfWeek

class CreateRoutineRequest(
    val routineName: String,
    val isArchived: Boolean,
    val isShared: Boolean,
    val exerciseInfoRequestList: MutableList<com.info.maeumgagym.core.routine.dto.request.ExerciseInfoRequest>,
    val dayOfWeeks: MutableSet<DayOfWeek>?
)
