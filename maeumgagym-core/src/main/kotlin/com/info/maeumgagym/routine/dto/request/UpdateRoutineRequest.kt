package com.info.maeumgagym.routine.dto.request

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import java.time.DayOfWeek

class UpdateRoutineRequest(
    val routineName: String,
    val isArchived: Boolean,
    val isShared: Boolean,
    val exerciseInfoDtoList: MutableList<ExerciseInfoDto>,
    val dayOfWeeks: MutableSet<DayOfWeek>?
)
