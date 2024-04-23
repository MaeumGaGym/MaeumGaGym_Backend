package com.info.maeumgagym.application.domain.purpose.repository

import com.info.maeumgagym.application.domain.purpose.entity.PurposeJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.util.*

@org.springframework.stereotype.Repository
interface PurposeRepository : Repository<PurposeJpaEntity, Long?> {

    fun save(purposeJpaEntity: PurposeJpaEntity): PurposeJpaEntity

    fun findById(id: Long): PurposeJpaEntity?

    fun findAllByUserId(userId: UUID, pageable: Pageable): List<PurposeJpaEntity>

    fun deleteById(id: Long)
}
