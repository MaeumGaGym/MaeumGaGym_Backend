package com.info.maeumgagym.routine.dto.response

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import java.time.LocalDate
import java.util.*

data class RoutineHistoryResponse(
    val id: Long,
    val routineName: String,
    val exerciseInfoList: List<ExerciseInfoDto>,
    val date: LocalDate
)
