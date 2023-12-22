package com.info.maeumgagym.domain.base

import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseLongIdTimeEntity(
    id: Long?,
    createdAt: LocalDateTime?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = id

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    val createdAt: LocalDateTime = createdAt ?: LocalDateTime.now()
}
