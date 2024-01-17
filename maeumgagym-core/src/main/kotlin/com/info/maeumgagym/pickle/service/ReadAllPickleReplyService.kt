package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.dto.response.PickleReplyListResponse
import com.info.maeumgagym.pickle.exception.PickleCommentNotFoundException
import com.info.maeumgagym.pickle.port.`in`.LoadAllPickleReplyUseCase
import com.info.maeumgagym.pickle.port.out.ReadAllReplyByParentCommentPort
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort

@UseCase
internal class ReadAllPickleReplyService(
    private val readAllReplyByParentCommentPort: ReadAllReplyByParentCommentPort,
    private val readPickleCommentPort: ReadPickleCommentPort
) : LoadAllPickleReplyUseCase {
    override fun loadAllPickleReply(parentCommentId: Long): PickleReplyListResponse {
        val replies = readAllReplyByParentCommentPort.readAllPickleReplyByParentComment(
            readPickleCommentPort.readPickleComment(parentCommentId) ?: throw PickleCommentNotFoundException
        )
        return PickleReplyListResponse(replies.map { it.toResponse(it) }.toList())
    }
}
