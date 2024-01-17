package com.info.maeumgagym.domain.pickle

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleCommentMapper
import com.info.maeumgagym.domain.pickle.repository.PickleReplyRepository
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply
import com.info.maeumgagym.pickle.port.out.ReadAllReplyByParentCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleReplyCommentPort

@PersistenceAdapter
internal class PickleReplyPersistenceAdapter(
    private val pickleReplyRepository: PickleReplyRepository,
    private val pickleCommentMapper: PickleCommentMapper
): SavePickleReplyCommentPort, ReadAllReplyByParentCommentPort {
    override fun savePickleReplyComment(pickleReply: PickleReply): PickleReply =
        pickleCommentMapper.toDomain(pickleReplyRepository.save(pickleCommentMapper.toEntity(pickleReply)))

    override fun readAllPickleReplyByParentComment(parentComment: PickleComment): List<PickleReply> {
        val pickleReplyList = pickleReplyRepository.findAllByParentComment(pickleCommentMapper.toEntity(parentComment))
        return pickleReplyList.map { pickleCommentMapper.toDomain(it) }.toList()
    }
}
