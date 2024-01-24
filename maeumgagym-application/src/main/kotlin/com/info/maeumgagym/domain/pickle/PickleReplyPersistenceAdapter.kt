package com.info.maeumgagym.domain.pickle

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleCommentMapper
import com.info.maeumgagym.domain.pickle.repository.PickleReplyRepository
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply
import com.info.maeumgagym.pickle.port.out.ReadAllReplyByParentCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleReplyCommentPort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class PickleReplyPersistenceAdapter(
    private val pickleReplyRepository: PickleReplyRepository,
    private val pickleCommentMapper: PickleCommentMapper
) : SavePickleReplyCommentPort, ReadAllReplyByParentCommentPort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun savePickleReplyComment(pickleReply: PickleReply): PickleReply =
        pickleCommentMapper.toDomain(
            pickleReplyRepository.save(pickleCommentMapper.toEntity(pickleReply))
        )

    override fun readAllPickleReplyByParentComment(parentComment: PickleComment): List<PickleReply> =
        pickleReplyRepository.findAllByParentComment(pickleCommentMapper.toEntity(parentComment))
            .map { pickleCommentMapper.toDomain(it) }.toList()
}
