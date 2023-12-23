package com.info.maeumgagym.routine.dto.request

import com.info.maeumgagym.routine.model.ExerciseInfoModel
import java.time.DayOfWeek

class CreateRoutineRequest(
    val routineName: String,
    val isArchived: Boolean,
    val isShared: Boolean,
    val exerciseInfoModelList: MutableList<ExerciseInfoModel> = ArrayList(),
    val dayOfWeeks: MutableSet<DayOfWeek>?
)
