package com.info.maeumgagym.core.pickle.service

import com.info.maeumgagym.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.common.exception.SecurityException
import com.info.maeumgagym.core.pickle.port.`in`.DeletePickleReplyUseCase
import com.info.maeumgagym.core.pickle.port.out.DeletePickleReplyPort
import com.info.maeumgagym.core.pickle.port.out.ReadPickleReplyPort

@UseCase
internal class DeletePickleReplyService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPickleReplyPort: ReadPickleReplyPort,
    private val pickleReplyPort: DeletePickleReplyPort
) : DeletePickleReplyUseCase {
    override fun deleteFromId(pickleReplyId: Long) {
        val user = readCurrentUserPort.readCurrentUser()
        val pickleReply = readPickleReplyPort.readById(pickleReplyId)
            ?: throw BusinessLogicException.PICKLE_REPLY_NOT_FOUND

        if (user.oauthId != pickleReply.writer.oauthId) {
            SecurityException.PERMISSION_DENIED
        }

        pickleReplyPort.delete(pickleReply)
    }
}
