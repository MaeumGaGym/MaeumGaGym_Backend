package com.info.maeumgagym.domain.routine.entity.history

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity(name = TableNames.ROUTINE_HISTORY_TABLE)
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = TableNames.ROUTINE_HISTORY_DATE_USER_UNIQUE,
            columnNames = ["user_id", "date"]
        )
    ]
)
class RoutineHistoryJpaEntity(
    id: Long?,
    userId: UUID,
    routineName: String,
    date: LocalDate
) : BaseLongIdEntity(id) {

    @Column(name = "routine_name", nullable = false)
    var routineName: String = routineName
        protected set

    @Column(name = "user_id", updatable = false, columnDefinition = "BINARY(16)", nullable = false)
    var userId: UUID = userId
        protected set

    @Column(name = "date", updatable = false, nullable = false)
    var date: LocalDate = date
        protected set
}
