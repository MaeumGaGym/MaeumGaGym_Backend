package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleReplyJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PickleReplyRepository : JpaRepository<PickleReplyJpaEntity, Long>
