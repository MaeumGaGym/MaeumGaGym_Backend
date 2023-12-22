package com.info.maeumgagym.domain.base

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

abstract class BaseUUIDTimeEntity(
    id: UUID?,
    createdAt: LocalDateTime?
) {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(
        columnDefinition = "BINARY(16)",
        nullable = false
    )
    val id: UUID? = id

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    val createdAt: LocalDateTime = createdAt ?: LocalDateTime.now()
}
