package com.info.maeumgagym.domain.base

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseUUIDEntity(
    id: UUID? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = id
}