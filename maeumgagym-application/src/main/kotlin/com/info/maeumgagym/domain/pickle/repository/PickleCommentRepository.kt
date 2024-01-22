package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleCommentRepository : Repository<PickleCommentJpaEntity, Long> {

    fun save(entity: PickleCommentJpaEntity): PickleCommentJpaEntity

    fun findAllByVideoId(videoId: String): List<PickleCommentJpaEntity>

    fun findById(id: Long): PickleCommentJpaEntity?
}
