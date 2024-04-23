package com.info.maeumgagym.application.domain.pose.repository

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.pose.entity.PoseJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

@org.springframework.stereotype.Repository
interface PoseNativeRepository : Repository<PoseJpaEntity, Long?> {

    @Query(
        value = "SELECT * FROM ${TableNames.POSE_TABLE} p " +
            "WHERE p.simple_part LIKE %:tag% OR p.exact_part LIKE %:tag% OR " +
            "p.simple_name LIKE %:tag% OR p.exact_name LIKE %:tag%",
        nativeQuery = true
    )
    fun findAllByTag(@Param("tag") tag: String): List<PoseJpaEntity>

    @Query(
        value = "SELECT * FROM ${TableNames.POSE_TABLE} p " +
            "ORDER BY RAND() " +
            "LIMIT 30",
        nativeQuery = true
    )
    fun readAllRandomLimit30(): List<PoseJpaEntity>
}
