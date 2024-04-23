package com.info.maeumgagym.application.domain.routine

import com.info.maeumgagym.application.domain.routine.entity.current.ExerciseInfoJpaEntity

object ExerciseInfoUtils {

    /**
     * @return 새로 저장해야 할 [ExerciseInfoJpaEntity] 목록, 삭제해야할 [ExerciseInfoJpaEntity] 목록
     */
    fun getEntitiesNeedSaveOrDelete(
        origin: List<ExerciseInfoJpaEntity>,
        changed: List<ExerciseInfoJpaEntity>
    ): Pair<List<ExerciseInfoJpaEntity>, List<ExerciseInfoJpaEntity>> {
        val result = Pair(mutableListOf<ExerciseInfoJpaEntity>(), mutableListOf<ExerciseInfoJpaEntity>())

        changed.forEach {
            if (!origin.isContains(it)) {
                result.first.add(it)
            }
        }

        origin.forEach {
            if (!changed.isContains(it)) {
                result.second.add(it)
            }
        }

        return result
    }

    private fun List<ExerciseInfoJpaEntity>.isContains(element: ExerciseInfoJpaEntity): Boolean {
        forEach {
            if (it.isEquals(element)) {
                return true
            }
        }
        return false
    }

    private fun ExerciseInfoJpaEntity.isEquals(another: ExerciseInfoJpaEntity): Boolean =
        routineId == another.routineId &&
            poseId == another.poseId &&
            repetitions == another.repetitions &&
            sets == another.sets
}
