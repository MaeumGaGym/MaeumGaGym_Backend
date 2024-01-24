package com.info.maeumgagym.domain.base

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.domain.Persistable
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseUUIDEntity(
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @get:JvmName("getIdForJvm")
    @Column(
        columnDefinition = "BINARY(16)",
        nullable = false
    )
    val id: UUID?
) : Persistable<UUID> {

    override fun getId(): UUID? = this.id

    override fun isNew(): Boolean = this.id == null
}
