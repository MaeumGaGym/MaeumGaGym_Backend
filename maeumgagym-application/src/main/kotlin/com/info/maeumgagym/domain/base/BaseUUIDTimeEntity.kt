package com.info.maeumgagym.domain.base

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseUUIDTimeEntity(
    id: UUID?,
    createdAt: LocalDateTime?
) : BaseTimeEntity(createdAt) {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(
        columnDefinition = "BINARY(16)",
        nullable = false
    )
    val id: UUID? = id
}
