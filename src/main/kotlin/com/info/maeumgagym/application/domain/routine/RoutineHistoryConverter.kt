package com.info.maeumgagym.application.domain.routine

import com.info.maeumgagym.application.domain.routine.entity.current.ExerciseInfoJpaEntity
import com.info.maeumgagym.application.domain.routine.entity.current.RoutineJpaEntity
import com.info.maeumgagym.application.domain.routine.entity.history.ExerciseInfoHistoryJpaEntity
import com.info.maeumgagym.application.domain.routine.entity.history.RoutineHistoryJpaEntity
import java.time.LocalDate

object RoutineHistoryConverter {

    fun convertRoutine(
        routineJpaEntity: RoutineJpaEntity,
        historyDate: LocalDate = LocalDate.now()
    ): RoutineHistoryJpaEntity = routineJpaEntity.run {
        RoutineHistoryJpaEntity(
            id = id,
            originId = routineJpaEntity.id!!,
            userId = userId,
            routineName = routineName,
            date = historyDate
        )
    }

    fun convertExerciseInfo(
        exerciseInfoJpaEntity: ExerciseInfoJpaEntity
    ): ExerciseInfoHistoryJpaEntity = exerciseInfoJpaEntity.run {
        ExerciseInfoHistoryJpaEntity(
            routineHistoryId = routineId,
            poseId = poseId,
            repetitions = repetitions,
            sets = sets,
            id = id
        )
    }
}
