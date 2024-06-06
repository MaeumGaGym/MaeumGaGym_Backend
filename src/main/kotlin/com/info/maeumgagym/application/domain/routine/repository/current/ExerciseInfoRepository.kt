package com.info.maeumgagym.application.domain.routine.repository.current

import com.info.maeumgagym.application.domain.routine.entity.current.ExerciseInfoJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface ExerciseInfoRepository : Repository<ExerciseInfoJpaEntity, Long?> {

    fun save(exerciseInfoEntity: ExerciseInfoJpaEntity): ExerciseInfoJpaEntity

    fun delete(exerciseInfoEntity: ExerciseInfoJpaEntity)

    fun deleteAllByRoutineId(routineId: Long)

    fun findAllByRoutineId(routineId: Long): List<ExerciseInfoJpaEntity>
}
