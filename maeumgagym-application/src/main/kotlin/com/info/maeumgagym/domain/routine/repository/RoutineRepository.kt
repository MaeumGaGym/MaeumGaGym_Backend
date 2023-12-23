package com.info.maeumgagym.domain.routine.repository

import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoutineRepository : JpaRepository<RoutineJpaEntity, UUID>
