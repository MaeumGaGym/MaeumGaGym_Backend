package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleReplyJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleReplyRepository : Repository<PickleReplyJpaEntity, Long> {

    fun findById(id: Long): PickleReplyJpaEntity?

    fun findAllByParentComment(
        pickleCommentJpaEntity: PickleCommentJpaEntity,
        pageable: Pageable
    ): Page<PickleReplyJpaEntity>

    fun save(entity: PickleReplyJpaEntity): PickleReplyJpaEntity

    fun delete(entity: PickleReplyJpaEntity)
}
