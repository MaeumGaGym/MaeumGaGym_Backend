package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PickleRepository : JpaRepository<PickleJpaEntity, Long>
