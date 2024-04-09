package com.info.maeumgagym.domain.routine.entity.current

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = TableNames.ROUTINE_EXERCISE_TABLE)
class ExerciseInfoJpaEntity(
    routineId: Long? = null,
    poseId: Long,
    repetitions: Int,
    sets: Int,
    id: Long? = null
) : BaseLongIdEntity(id) {

    @Column(name = "routine_id", updatable = false, nullable = false)
    val routineId: Long? = routineId

    @Column(name = "pose_id", updatable = false, nullable = false)
    val poseId: Long = poseId

    @Column(name = "repetitions", updatable = false, nullable = false)
    val repetitions: Int = repetitions

    @Column(name = "sets", updatable = false, nullable = false)
    val sets: Int = sets
}
