package com.info.maeumgagym.application.domain.pickle.repository

import com.info.maeumgagym.application.domain.pickle.entity.PickleCommentMappedEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleCommentMappedRepository : Repository<PickleCommentMappedEntity, Long>
