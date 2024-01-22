package com.info.maeumgagym.domain.user.repository

import com.info.maeumgagym.domain.user.entity.DeletedAtJpaEntity
import org.springframework.data.repository.Repository
import java.util.*

@org.springframework.stereotype.Repository
interface DeletedAtRepository : Repository<DeletedAtJpaEntity, UUID> {

    fun save(entity: DeletedAtJpaEntity): DeletedAtJpaEntity

    fun findByUserId(id: UUID): DeletedAtJpaEntity?

    fun deleteByUserId(id: UUID)
}
