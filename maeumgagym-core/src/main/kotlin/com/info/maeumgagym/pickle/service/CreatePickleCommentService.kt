package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.request.PickleCommentRequest
import com.info.maeumgagym.pickle.exception.PickleCommentNotFoundException
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.port.`in`.CreatePickleCommentUseCase
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleCommentPort

@UseCase
class CreatePickleCommentService(
    private val savePickleCommentPort: SavePickleCommentPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPickleCommentPort: ReadPickleCommentPort
): CreatePickleCommentUseCase {
    override fun createPickleComment(pickleCommentRequest: PickleCommentRequest) {
        val user = readCurrentUserPort.readCurrentUser()
        val comment = pickleCommentRequest.run {
            savePickleCommentPort.savePickleComment(
                PickleComment(
                    content = content,
                    pickleId = pickleId,
                    writerId = user.id,
                    parentComment = pickleCommentRequest.parentId?.let { readPickleCommentPort.readPickleComment(it) ?: throw PickleCommentNotFoundException }
                )
            )
        }
    }
}
