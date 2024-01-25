package com.info.maeumgagym.domain.report.repository

import com.info.maeumgagym.domain.report.entity.ReportJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportRepository : JpaRepository<ReportJpaEntity, Long>
