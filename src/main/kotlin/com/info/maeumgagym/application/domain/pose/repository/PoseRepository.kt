package com.info.maeumgagym.application.domain.pose.repository

import com.info.maeumgagym.application.domain.pose.entity.PoseJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PoseRepository : Repository<PoseJpaEntity, Long?> {

    fun save(poseJpaEntity: PoseJpaEntity): PoseJpaEntity

    fun findById(id: Long): PoseJpaEntity?

    fun findAll(): List<PoseJpaEntity>
}
