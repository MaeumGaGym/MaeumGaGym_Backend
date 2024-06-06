package com.info.maeumgagym.core.purpose.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.purpose.model.Purpose
import com.info.maeumgagym.core.purpose.port.`in`.DeletePurposeUseCase
import com.info.maeumgagym.core.purpose.port.out.DeletePurposePort
import com.info.maeumgagym.core.purpose.port.out.ReadPurposePort

@UseCase
internal class DeletePurposeService(
    private val readPurposePort: ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val deletePurposePort: DeletePurposePort
) : DeletePurposeUseCase {

    override fun deletePurposeFromId(id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose: Purpose = readPurposePort.readById(id)
            ?.takeIf { it.user == user } ?: throw BusinessLogicException.PURPOSE_NOT_FOUND

        deletePurposePort.deleteById(purpose.id!!)
    }
}
