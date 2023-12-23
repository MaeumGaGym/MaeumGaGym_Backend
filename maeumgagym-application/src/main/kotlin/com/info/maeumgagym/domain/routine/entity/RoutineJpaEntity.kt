package com.info.maeumgagym.domain.routine.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseEntity
import java.time.DayOfWeek
import java.util.*
import javax.persistence.*

@Entity(name = TableNames.ROUTINE_TABLE)
class RoutineJpaEntity(
    id: UUID?,
    routineName: String,
    exerciseInfoList: MutableList<ExerciseInfo>,
    dayOfWeeks: MutableSet<DayOfWeek>?,
    routineStatus: RoutineStatus
) : BaseEntity(id) {
    @Column(name = "routine_name", nullable = false)
    var routineName: String = routineName
        protected set

    @ElementCollection
    var exerciseInfoList: MutableList<ExerciseInfo> = exerciseInfoList
        protected set

    @ElementCollection
    @Enumerated(EnumType.STRING)
    var dayOfWeeks: MutableSet<DayOfWeek>? = dayOfWeeks
        protected set

    @Embedded
    @Column(name = "routine_status", nullable = false)
    var routineStatus: RoutineStatus = routineStatus
        protected set
}
