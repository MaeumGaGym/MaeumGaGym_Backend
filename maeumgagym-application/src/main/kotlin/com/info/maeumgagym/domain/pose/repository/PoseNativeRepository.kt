package com.info.maeumgagym.domain.pose.repository

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.pose.entity.PoseJpaEntity
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

@org.springframework.stereotype.Repository
interface PoseNativeRepository : Repository<RoutineJpaEntity, Long?> {

    @Query(
        value = "SELECT * FROM ${TableNames.POSE_TABLE} p " +
            "WHERE p.simple_part LIKE :tag OR p.exact_part LIKE :tag OR " +
            "p.simple_name LIKE :tag OR p.exact_name LIKE :tag",
        nativeQuery = true
    )
    fun readByTag(@Param("tag") tag: String): List<PoseJpaEntity>
}
