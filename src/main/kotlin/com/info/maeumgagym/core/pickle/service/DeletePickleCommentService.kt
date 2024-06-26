package com.info.maeumgagym.core.pickle.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.core.pickle.port.`in`.DeletePickleCommentUseCase
import com.info.maeumgagym.core.pickle.port.out.DeletePickleCommentPort
import com.info.maeumgagym.core.pickle.port.out.ReadPickleCommentPort

@UseCase
internal class DeletePickleCommentService(
    private val deletePickleCommentPort: DeletePickleCommentPort,
    private val currentUserPort: ReadCurrentUserPort,
    private val readPickleCommentPort: ReadPickleCommentPort
) : DeletePickleCommentUseCase {
    override fun deleteFromId(pickleCommentId: Long) {
        val user = currentUserPort.readCurrentUser()
        val pickleComment = readPickleCommentPort.readById(pickleCommentId)
            ?: throw BusinessLogicException.PICKLE_COMMENT_NOT_FOUND

        if (pickleComment.writer.oauthId != user.oauthId) {
            throw SecurityException.PERMISSION_DENIED
        }

        deletePickleCommentPort.delete(pickleComment)
    }
}
