package com.info.maeumgagym.core.routine.model

import com.info.maeumgagym.core.routine.dto.response.ExerciseInfoResponse
import com.info.maeumgagym.core.routine.dto.response.RoutineHistoryResponse
import java.time.LocalDate
import java.util.*

data class RoutineHistory(
    val id: Long?,
    val userId: UUID,
    val routineName: String,
    val date: LocalDate,
    val exerciseInfoHistoryList: List<com.info.maeumgagym.core.routine.model.ExerciseInfoHistoryModel>
) {
    fun toResponse(): com.info.maeumgagym.core.routine.dto.response.RoutineHistoryResponse =
        com.info.maeumgagym.core.routine.dto.response.RoutineHistoryResponse(
            id = id!!,
            routineName = routineName,
            exerciseInfoResponseList = exerciseInfoHistoryList.map {
                com.info.maeumgagym.core.routine.dto.response.ExerciseInfoResponse(
                    pose = it.pose.toInfoResponse(),
                    repetitions = it.repetitions,
                    sets = it.sets
                )
            },
            date = date
        )
}
