package com.info.maeumgagym.domain.user.repository

import com.info.maeumgagym.domain.user.entity.DeletedAtJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeletedAtRepository : JpaRepository<DeletedAtJpaEntity, UUID> {
}
