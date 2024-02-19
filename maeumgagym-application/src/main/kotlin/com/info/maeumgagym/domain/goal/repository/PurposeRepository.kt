package com.info.maeumgagym.domain.goal.repository

import com.info.maeumgagym.domain.goal.entity.PurposeJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PurposeRepository : Repository<PurposeJpaEntity, Long?> {

    fun save(purposeJpaEntity: PurposeJpaEntity): PurposeJpaEntity
}
