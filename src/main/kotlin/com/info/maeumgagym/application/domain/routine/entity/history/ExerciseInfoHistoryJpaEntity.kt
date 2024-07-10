package com.info.maeumgagym.application.domain.routine.entity.history

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.base.BaseLongIdEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = TableNames.ROUTINE_EXERCISE_HISTORY_TABLE)
class ExerciseInfoHistoryJpaEntity(
    routineHistoryId: Long? = null,
    poseId: Long,
    repetitions: Int,
    sets: Int,
    weightKilogram: Int? = null,
    id: Long? = null
) : BaseLongIdEntity(id) {

    @Column(name = "routine_history_id", updatable = false, nullable = false)
    var routineHistoryId: Long? = routineHistoryId
        protected set

    @Column(name = "pose_id", updatable = false, nullable = false)
    var poseId: Long = poseId
        protected set

    @Column(name = "repetitions", updatable = false, nullable = false)
    var repetitions: Int = repetitions
        protected set

    @Column(name = "sets", updatable = false, nullable = false)
    var sets: Int = sets
        protected set

    @Column(name = "weight_kilogram", updatable = false, nullable = true)
    val weightKilogram: Int? = weightKilogram
}
