package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleCommentRepository : Repository<PickleCommentJpaEntity, Long> {

    fun save(entity: PickleCommentJpaEntity): PickleCommentJpaEntity

    fun findAllByVideoId(videoId: String, pageable: Pageable): Page<PickleCommentJpaEntity>

    fun findById(id: Long): PickleCommentJpaEntity?
}
