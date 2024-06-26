package com.info.maeumgagym.application.domain.base

import javax.persistence.*

@MappedSuperclass
abstract class BaseLongIdEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long?
)
