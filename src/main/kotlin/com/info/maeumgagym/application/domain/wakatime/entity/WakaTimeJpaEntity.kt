package com.info.maeumgagym.application.domain.wakatime.entity

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.base.BaseUUIDEntity
import com.info.maeumgagym.application.domain.user.entity.UserJpaEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(
    name = TableNames.WAKA_TIME_TABLE,
    uniqueConstraints = [
        UniqueConstraint(
            name = "UNIQUE_USER_AND_DATE",
            columnNames = ["user_id", "date"]
        )
    ]
)
class WakaTimeJpaEntity(
    user: UserJpaEntity,
    waka: Long,
    date: LocalDate,
    id: UUID? = null
) : BaseUUIDEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
    var user: UserJpaEntity = user
        protected set

    @Column(name = "date", nullable = false)
    var date: LocalDate = date
        protected set

    @Column(name = "seconds", nullable = false)
    var waka: Long = waka
        protected set
}
