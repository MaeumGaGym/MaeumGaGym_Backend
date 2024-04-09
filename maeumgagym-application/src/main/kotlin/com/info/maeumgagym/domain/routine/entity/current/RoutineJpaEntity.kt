package com.info.maeumgagym.domain.routine.entity.current

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdTimeEntity
import com.info.maeumgagym.domain.routine.entity.RoutineStatus
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = TableNames.ROUTINE_TABLE)
class RoutineJpaEntity(
    routineName: String,
    dayOfWeeks: MutableSet<DayOfWeek>?,
    routineStatus: RoutineStatus,
    createdAt: LocalDateTime? = null,
    id: Long? = null,
    userId: UUID
) : BaseLongIdTimeEntity(id, createdAt) {
    @Column(name = "routine_name", nullable = false)
    var routineName: String = routineName
        protected set

    @ElementCollection
    @Enumerated(EnumType.STRING)
    var dayOfWeeks: MutableSet<DayOfWeek>? = dayOfWeeks
        protected set

    @Embedded
    @Column(name = "routine_status", nullable = false)
    var routineStatus: RoutineStatus = routineStatus
        protected set

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    var userId: UUID = userId
        protected set
}
