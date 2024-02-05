package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.exception.PickleReplyNotFoundException
import com.info.maeumgagym.pickle.port.`in`.DeletePickleReplyUseCase
import com.info.maeumgagym.pickle.port.out.DeletePickleReplyPort
import com.info.maeumgagym.pickle.port.out.ReadPickleReplyPort

@UseCase
internal class DeletePickleReplyService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPickleReplyPort: ReadPickleReplyPort,
    private val pickleReplyPort: DeletePickleReplyPort
) : DeletePickleReplyUseCase {
    override fun deleteFromId(pickleReplyId: Long) {
        val user = readCurrentUserPort.readCurrentUser()
        val pickleReply = readPickleReplyPort.readById(pickleReplyId) ?: throw PickleReplyNotFoundException

        if (user.oauthId != pickleReply.writer.oauthId) {
            throw PermissionDeniedException
        }

        pickleReplyPort.delete(pickleReply)
    }
}
