package com.info.maeumgagym.core.purpose.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException

@UseCase
internal class DeletePurposeService(
    private val readPurposePort: com.info.maeumgagym.core.purpose.port.out.ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val deletePurposePort: com.info.maeumgagym.core.purpose.port.out.DeletePurposePort
) : com.info.maeumgagym.core.purpose.port.`in`.DeletePurposeUseCase {

    override fun deletePurposeFromId(id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose: com.info.maeumgagym.core.purpose.model.Purpose = readPurposePort.readById(id)
            ?.takeIf { it.user == user } ?: throw BusinessLogicException.PURPOSE_NOT_FOUND

        deletePurposePort.deleteById(purpose.id!!)
    }
}
