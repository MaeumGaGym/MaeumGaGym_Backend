package com.info.maeumgagym.core.routine.model

import com.info.maeumgagym.core.routine.dto.RoutineStatusDto
import com.info.maeumgagym.core.routine.dto.response.RoutineResponse
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

data class Routine(
    val id: Long? = null,
    val routineName: String,
    val exerciseInfoModelList: MutableList<ExerciseInfoModel>,
    val dayOfWeeks: MutableSet<DayOfWeek>?,
    val routineStatusModel: RoutineStatusModel,
    val userId: UUID
) {
    fun toResponse(): RoutineResponse =
        RoutineResponse(
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
                RoutineStatusDto(
                    isArchived = isArchived,
                    isShared = isShared
                )
            }
        )

    fun toResponse(isCompleted: Boolean): RoutineResponse =
        RoutineResponse(
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
                RoutineStatusDto(
                    isArchived = isArchived,
                    isShared = isShared
                )
            },
            isCompleted = isCompleted
        )
}
