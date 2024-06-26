package com.info.maeumgagym.application.domain.purpose.entity

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.base.BaseLongIdEntity
import com.info.maeumgagym.application.domain.user.entity.UserJpaEntity
import java.time.LocalDate
import javax.persistence.*

@Entity(name = TableNames.PURPOSE_TABLE)
@Table(
    name = TableNames.PURPOSE_TABLE,
    indexes = [
        Index(name = TableNames.PURPOSE_START_DATE_INDEX, columnList = "start_date"),
        Index(name = TableNames.PURPOSE_END_DATE_INDEX, columnList = "end_date")
    ]
)
class PurposeJpaEntity(
    title: String,
    content: String,
    startDate: LocalDate,
    endDate: LocalDate,
    user: UserJpaEntity,
    id: Long? = null
) : BaseLongIdEntity(id) {

    @Column(name = "title", updatable = true, nullable = false)
    var title: String = title
        protected set

    @Column(name = "content", updatable = true, nullable = false)
    var content: String = content
        protected set

    @Column(name = "start_date", updatable = true, nullable = false)
    var startDate: LocalDate = startDate
        protected set

    @Column(name = "end_date", updatable = true, nullable = false)
    var endDate: LocalDate = endDate
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    var user: UserJpaEntity = user
        protected set
}
