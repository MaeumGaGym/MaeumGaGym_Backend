package com.info.maeumgagym.domain.user.entity

import com.info.maeumgagym.TableNames
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity(name = TableNames.DELETED_USER_TABLE)
class DeletedUserJpaEntity(
    @Id
    @Column(name = "id")
    var id: UUID? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", columnDefinition = "BINARY(16)")
    var userJpaEntity: UserJpaEntity,

    @Column(name = "deleted_at", nullable = false)
    val deletedAt: LocalDate
)
