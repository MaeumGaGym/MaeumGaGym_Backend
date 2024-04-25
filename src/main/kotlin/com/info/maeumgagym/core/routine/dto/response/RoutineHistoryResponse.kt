package com.info.maeumgagym.core.routine.dto.response

import java.time.LocalDate

data class RoutineHistoryResponse(
    val id: Long,
    val routineName: String,
    val exerciseInfoResponseList: List<ExerciseInfoResponse>,
    val date: LocalDate
)
