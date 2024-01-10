package com.info.maeumgagym.domain.base

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseTimeEntity(
    createdAt: LocalDateTime?
) {
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    val createdAt: LocalDateTime = createdAt ?: LocalDateTime.now()
}
