package com.info.maeumgagym.error

import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface ErrorLogRepository : Repository<ErrorLogJpaEntity, String> {

    fun save(errorLogJpaEntity: ErrorLogJpaEntity): ErrorLogJpaEntity

    fun findById(id: String): ErrorLogJpaEntity?
}
