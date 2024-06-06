package com.info.maeumgagym.application.domain.routine.repository.history

import com.info.maeumgagym.application.domain.routine.entity.history.ExerciseInfoHistoryJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface ExerciseInfoHistoryRepository : Repository<ExerciseInfoHistoryJpaEntity, Long?> {

    fun save(exerciseInfoHistoryJpaEntityList: ExerciseInfoHistoryJpaEntity)

    fun findAllByRoutineHistoryId(routineHistoryId: Long): List<ExerciseInfoHistoryJpaEntity>
}
