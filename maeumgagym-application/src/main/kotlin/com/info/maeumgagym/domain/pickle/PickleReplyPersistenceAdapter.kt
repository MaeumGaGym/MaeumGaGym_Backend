package com.info.maeumgagym.domain.pickle

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleCommentMapper
import com.info.maeumgagym.domain.pickle.repository.PickleCommentRepository
import com.info.maeumgagym.domain.pickle.repository.PickleReplyRepository
import com.info.maeumgagym.pickle.model.PickleReply
import com.info.maeumgagym.pickle.port.out.SavePickleReplyCommentPort

@PersistenceAdapter
class PickleReplyPersistenceAdapter(
    private val pickleReplyRepository: PickleReplyRepository,
    private val pickleCommentMapper: PickleCommentMapper
): SavePickleReplyCommentPort {
    override fun savePickleReplyComment(pickleReply: PickleReply) {
        pickleReplyRepository.save(pickleCommentMapper.toEntity(pickleReply))
    }
}
