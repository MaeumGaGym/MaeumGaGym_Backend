package com.info.maeumgagym.domain.daily.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(
    name = TableNames.DAILY,
    uniqueConstraints = [
        UniqueConstraint(
            name = TableNames.DAILY_DATE_UPLOADER_UNIQUE,
            columnNames = ["uploader_id", "date"]
        )
    ],
    indexes = [Index(name = TableNames.DAILY_DATE_INDEX, columnList = "date")]
)
class DailyJpaEntity(
    id: Long?,
    title: String,
    uploader: UserJpaEntity,
    date: LocalDate,
    time: LocalTime
) : BaseLongIdEntity(id) {

    @Column(name = "title", nullable = false, length = 15)
    var title: String = title
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    var uploader: UserJpaEntity = uploader
        protected set

    @Column(name = "date", nullable = false)
    var date: LocalDate = date
        protected set

    @Column(name = "time", nullable = false)
    var time: LocalTime = time
        protected set
}
