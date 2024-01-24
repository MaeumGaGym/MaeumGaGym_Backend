package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleRepository : Repository<PickleJpaEntity, String> {

    fun save(entity: PickleJpaEntity): PickleJpaEntity

    fun delete(entity: PickleJpaEntity)

    fun findByVideoId(id: String): PickleJpaEntity?

    fun findAll(): List<PickleJpaEntity>
}
