package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PickleCommentRepository : JpaRepository<PickleCommentJpaEntity, Long> {
    fun findAllByVideoId(videoId: String, pageable: Pageable): Page<PickleCommentJpaEntity>
}
