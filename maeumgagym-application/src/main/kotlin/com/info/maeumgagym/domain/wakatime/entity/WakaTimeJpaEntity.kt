package com.info.maeumgagym.domain.wakatime.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity(name = TableNames.WAKA_TIME_TABLE)
class WakaTimeJpaEntity(
    user: UserJpaEntity,
    waka: Long,
    date: LocalDate,
    id: UUID? = null
) {

    @Id @Column(name = "id")
    var id: UUID? = id
        protected set

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE]) @MapsId
    @JoinColumn(name = "id", columnDefinition = "BINARY(16)")
    var user: UserJpaEntity = user
        protected set

    @Column(name = "date", nullable = false)
    var date: LocalDate = date
        protected set

    @Column(name = "seconds", nullable = false)
    var waka: Long = waka
        protected set
}
