package com.info.maeumgagym.domain.pickle

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleCommentMapper
import com.info.maeumgagym.domain.pickle.repository.PickleCommentRepository
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.port.out.ReadAllPagedPickleCommentsByVideoIdPort
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleCommentPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull

@PersistenceAdapter
internal class PickleCommentPersistenceAdapter(
    private val pickleCommentRepository: PickleCommentRepository,
    private val pickleCommentMapper: PickleCommentMapper
): SavePickleCommentPort, ReadPickleCommentPort, ReadAllPagedPickleCommentsByVideoIdPort {
    override fun readPickleComment(pickleCommentId: Long): PickleComment? =
        pickleCommentRepository.findByIdOrNull(pickleCommentId)?.let { pickleCommentMapper.toDomain(it) }

    override fun savePickleComment(pickleComment: PickleComment): PickleComment =
        pickleCommentMapper.toDomain(pickleCommentRepository.save(pickleCommentMapper.toEntity(pickleComment)))

    override fun readAllPickleCommentsByVideoId(videoId: String, page: Int, size: Int): List<PickleComment> =
        pickleCommentRepository.findAllByVideoId(videoId, PageRequest.of(page, size) as Pageable).content
            .map { pickleCommentMapper.toDomain(it) }.toList()
}
