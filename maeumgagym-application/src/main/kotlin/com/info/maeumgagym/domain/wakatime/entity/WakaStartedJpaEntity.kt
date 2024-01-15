package com.info.maeumgagym.domain.wakatime.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = TableNames.WAKA_START_TABLE)
class WakaStartedJpaEntity(
    user: UserJpaEntity,
    startAt: LocalDateTime,
    id: UUID? = null
) {

    @Id @Column(name = "id")
    var id: UUID? = id
        protected set

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE]) @MapsId
    @JoinColumn(name = "id", columnDefinition = "BINARY(16)")
    var user: UserJpaEntity = user
        protected set

    @Column(name = "start_at")
    var startAt: LocalDateTime = startAt
        protected set
}
