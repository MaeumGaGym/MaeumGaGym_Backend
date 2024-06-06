package com.info.maeumgagym.application.domain.base

import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseLongIdTimeEntity(
    id: Long?,
    createdAt: LocalDateTime?
) : BaseTimeEntity(createdAt) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = id
}
