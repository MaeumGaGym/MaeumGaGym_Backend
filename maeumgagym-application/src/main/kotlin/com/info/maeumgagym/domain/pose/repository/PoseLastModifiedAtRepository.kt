package com.info.maeumgagym.domain.pose.repository

import com.info.maeumgagym.domain.pose.entity.PoseLastModifiedAtEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PoseLastModifiedAtRepository : Repository<PoseLastModifiedAtEntity, String> {

    fun findById(id: String): PoseLastModifiedAtEntity?

    fun save(poseLastModifiedAtEntity: PoseLastModifiedAtEntity): PoseLastModifiedAtEntity
}
