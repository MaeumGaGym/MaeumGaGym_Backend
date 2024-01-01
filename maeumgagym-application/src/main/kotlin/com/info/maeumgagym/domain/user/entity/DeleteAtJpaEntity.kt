package com.info.maeumgagym.domain.user.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = TableNames.DELETED_AT_TABLE)
class DeleteAtJpaEntity(
    userId: UUID? = null,
    date: LocalDate = LocalDate.now()
): BaseUUIDEntity(userId) {

    @Column(name = "date", nullable = false)
    var date: LocalDate = date
        protected set
}
