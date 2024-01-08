package com.info.maeumgagym.domain.user.entity

import com.info.maeumgagym.TableNames
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = TableNames.DELETED_AT_TABLE)
class DeletedAtJpaEntity(
    userId: UUID,
    date: LocalDate = LocalDate.now()
) {

    @Column(name = "date", nullable = false)
    var date: LocalDate = date
        protected set

    @Id
    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    val userId: UUID = userId
}
