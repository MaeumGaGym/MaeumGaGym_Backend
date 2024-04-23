package com.info.maeumgagym.core.routine.model

import com.info.maeumgagym.core.routine.dto.RoutineStatusDto
import com.info.maeumgagym.core.routine.dto.response.RoutineResponse
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

data class Routine(
    val id: Long? = null,
    val routineName: String,
    val exerciseInfoModelList: MutableList<com.info.maeumgagym.core.routine.model.ExerciseInfoModel>,
    val dayOfWeeks: MutableSet<DayOfWeek>?,
    val routineStatusModel: com.info.maeumgagym.core.routine.model.RoutineStatusModel,
    val userId: UUID
) {
    fun toResponse(): com.info.maeumgagym.core.routine.dto.response.RoutineResponse =
        com.info.maeumgagym.core.routine.dto.response.RoutineResponse(
            id = id!!,
            routineName = routineName,
            exerciseInfoResponseList = exerciseInfoModelList.map {
                it.toResponse()
            },
            dayOfWeeks = dayOfWeeks?.sorted()?.map {
                it.getDisplayName(
                    TextStyle.SHORT,
                    Locale.KOREA
                )
            },
            routineStatus = routineStatusModel.run {
                com.info.maeumgagym.core.routine.dto.RoutineStatusDto(
                    isArchived = isArchived,
                    isShared = isShared
                )
            }
        )

    fun toResponse(isCompleted: Boolean): com.info.maeumgagym.core.routine.dto.response.RoutineResponse =
        com.info.maeumgagym.core.routine.dto.response.RoutineResponse(
            id = id!!,
            routineName = routineName,
            exerciseInfoResponseList = exerciseInfoModelList.map {
                it.toResponse()
            },
            dayOfWeeks = dayOfWeeks?.sorted()?.map {
                it.getDisplayName(
                    TextStyle.SHORT,
                    Locale.KOREA
                )
            },
            routineStatus = routineStatusModel.run {
                com.info.maeumgagym.core.routine.dto.RoutineStatusDto(
                    isArchived = isArchived,
                    isShared = isShared
                )
            },
            isCompleted = isCompleted
        )
}
