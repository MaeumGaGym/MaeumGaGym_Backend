package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleLikeRepository : Repository<PickleLikeJpaEntity, PickleLikeJpaEntity.IdClass> {

    fun save(pickleLikeJpaEntity: PickleLikeJpaEntity): PickleLikeJpaEntity

    fun findById(id: PickleLikeJpaEntity.IdClass): PickleLikeJpaEntity?

    fun delete(pickleLikeJpaEntity: PickleLikeJpaEntity)
}
