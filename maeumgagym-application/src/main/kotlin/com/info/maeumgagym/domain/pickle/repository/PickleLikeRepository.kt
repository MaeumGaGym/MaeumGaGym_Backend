package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PickleLikeRepository : JpaRepository<PickleLikeJpaEntity, PickleLikeJpaEntity.IdClass>
