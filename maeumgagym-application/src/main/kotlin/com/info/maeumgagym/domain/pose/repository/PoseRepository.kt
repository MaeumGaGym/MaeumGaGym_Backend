package com.info.maeumgagym.domain.pose.repository

import com.info.maeumgagym.domain.pose.entity.PoseJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PoseRepository : Repository<PoseJpaEntity, Long?> {

    fun save(poseJpaEntity: PoseJpaEntity): PoseJpaEntity

    fun findById(id: Long): PoseJpaEntity?

    fun findAll(): List<PoseJpaEntity>
}
