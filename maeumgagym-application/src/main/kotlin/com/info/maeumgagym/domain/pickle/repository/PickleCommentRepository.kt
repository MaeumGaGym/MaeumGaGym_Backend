package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PickleCommentRepository : JpaRepository<PickleCommentJpaEntity, Long>
