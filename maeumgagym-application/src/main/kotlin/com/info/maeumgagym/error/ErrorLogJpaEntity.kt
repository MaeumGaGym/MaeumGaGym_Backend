package com.info.maeumgagym.error

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "tbl_error_log")
class ErrorLogJpaEntity(
    status: Int = 500,
    message: String?,
    log: String,
    layer: String,
    timestamp: LocalDateTime,
    id: String
) {

    @Column(name = "status", updatable = false, nullable = false)
    var status: Int = status
        protected set

    @Column(name = "message", updatable = false, nullable = true)
    var message: String? = message
        protected set

    @Column(name = "log", updatable = false, nullable = false)
    var log: String = log
        protected set

    @Column(name = "layer", updatable = false, nullable = false)
    var layer: String = layer
        protected set

    @Column(name = "timestamp", updatable = false, nullable = false)
    var timestamp: LocalDateTime = timestamp
        protected set

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    var id: String = id
        protected set
}
