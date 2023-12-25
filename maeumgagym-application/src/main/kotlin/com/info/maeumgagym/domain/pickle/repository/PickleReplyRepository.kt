package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleReplyJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PickleReplyRepository : JpaRepository<PickleReplyJpaEntity, Long>
