package com.info.maeumgagym.domain.wakatime.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@Entity(name = TableNames.WAKA_START_TABLE)
class WakaStartJpaEntity(
    user: UserJpaEntity,
    startAt: LocalDateTime
) {

    @Id @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID = user.id!!
        protected set


    @OneToOne(fetch = FetchType.LAZY) @MapsId
    @JoinColumn(name = "id", columnDefinition = "BINARY(16)")
    var user: UserJpaEntity = user
        protected set

    @Column(name = "start_at")
    var startAt: LocalDateTime = startAt
        protected set
}
