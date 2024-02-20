package com.info.maeumgagym.domain.purpose.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import java.time.LocalDate
import javax.persistence.*

@Entity(name = TableNames.PURPOSE_TABLE)
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
