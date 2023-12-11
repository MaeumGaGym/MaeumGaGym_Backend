package com.info.maeumgagym.domain.pose.repository

import com.info.maeumgagym.domain.pose.entity.PoseJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PoseRepository: JpaRepository<PoseJpaEntity, UUID?> {
}
