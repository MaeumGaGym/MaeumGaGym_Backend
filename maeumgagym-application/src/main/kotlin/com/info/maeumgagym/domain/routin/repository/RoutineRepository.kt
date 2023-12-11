package com.info.maeumgagym.domain.routin.repository

import com.info.maeumgagym.domain.routin.entity.RoutineJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoutineRepository : JpaRepository<RoutineJpaEntity, UUID>
