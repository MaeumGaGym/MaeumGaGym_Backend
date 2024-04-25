package com.info.maeumgagym.application.domain.pickle.repository

import com.info.maeumgagym.application.domain.pickle.entity.PickleJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleRepository : Repository<PickleJpaEntity, String> {

    fun save(entity: PickleJpaEntity): PickleJpaEntity

    fun delete(entity: PickleJpaEntity)

    fun findById(id: String): PickleJpaEntity?

    fun findAll(): List<PickleJpaEntity>
}
