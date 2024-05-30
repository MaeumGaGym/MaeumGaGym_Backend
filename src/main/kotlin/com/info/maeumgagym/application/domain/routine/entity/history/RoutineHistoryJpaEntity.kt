package com.info.maeumgagym.application.domain.routine.entity.history

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.base.BaseLongIdEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Entity(name = TableNames.ROUTINE_HISTORY_TABLE)
@Table(
    indexes = [
        Index(
            name = TableNames.ROUTINE_HISTORY_DATE_USER,
            columnList = "user_id, date"
        )
    ]
)
class RoutineHistoryJpaEntity(
    id: Long?,
    originId: Long,
    userId: UUID,
    routineName: String,
    date: LocalDate
) : BaseLongIdEntity(id) {

    @Column(name = "origin_id", updatable = false, nullable = false)
    var originId: Long = originId
        protected set


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
