package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleUserMap
import org.springframework.data.jpa.repository.JpaRepository

interface PickleLikeRepository : JpaRepository<PickleLikeJpaEntity, PickleUserMap>
