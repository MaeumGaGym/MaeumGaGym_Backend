package com.info.maeumgagym.routine.model

import java.time.LocalDate
import java.util.*

data class RoutineHistory(
    val id: Long?,
    val userId: UUID,
    val date: LocalDate,
    val exerciseInfoList: MutableList<ExerciseInfoModel>
)
