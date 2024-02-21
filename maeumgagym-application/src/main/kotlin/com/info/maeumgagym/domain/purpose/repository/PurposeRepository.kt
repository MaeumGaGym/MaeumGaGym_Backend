package com.info.maeumgagym.domain.purpose.repository

import com.info.maeumgagym.domain.purpose.entity.PurposeJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PurposeRepository : Repository<PurposeJpaEntity, Long?> {

    fun save(purposeJpaEntity: PurposeJpaEntity): PurposeJpaEntity
}
