package com.info.maeumgagym.domain.purpose.repository

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.purpose.entity.PurposeJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.UUID

@org.springframework.stereotype.Repository
interface PurposeNativeRepository : Repository<PurposeJpaEntity, Long?> {

    @Query(
        value = "SELECT * " +
            "FROM ${TableNames.PURPOSE_TABLE} p " +
            "WHERE (p.user_id = :userId) AND " +
            "((p.start_date BETWEEN :startDate AND :endDate) OR " +
            "(p.end_date BETWEEN :startDate AND :endDate) OR " +
            "(p.start_date < :startDate AND p.end_date > :endDate))",
        nativeQuery = true
    )
    fun findAllByUserIdAndDateBetween(
        @Param("userId") userId: UUID,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<PurposeJpaEntity>
}
