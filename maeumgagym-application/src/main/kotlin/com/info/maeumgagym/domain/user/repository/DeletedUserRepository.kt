package com.info.maeumgagym.domain.user.repository

import com.info.maeumgagym.domain.user.entity.DeletedUserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DeletedUserRepository : JpaRepository<DeletedUserJpaEntity, UUID>
