package com.info.maeumgagym.domain.routine.entity.history

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = TableNames.ROUTINE_EXERCISE_HISTORY_TABLE)
class ExerciseInfoHistoryJpaEntity(
    routineHistoryId: Long? = null,
    poseId: Long,
    repetitions: Int,
    sets: Int,
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
}
