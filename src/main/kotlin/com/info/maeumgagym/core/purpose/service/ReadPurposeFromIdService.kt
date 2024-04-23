package com.info.maeumgagym.core.purpose.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.common.exception.SecurityException

@ReadOnlyUseCase
class ReadPurposeFromIdService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPurposePort: com.info.maeumgagym.core.purpose.port.out.ReadPurposePort
) : com.info.maeumgagym.core.purpose.port.`in`.ReadPurposeFromIdUseCase {

    override fun readPurposeFromId(id: Long): com.info.maeumgagym.core.purpose.dto.response.PurposeInfoResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose = readPurposePort.readById(id) ?: throw BusinessLogicException.PURPOSE_NOT_FOUND

        if (purpose.user.id != user.id) throw SecurityException.PERMISSION_DENIED

        return purpose.toResponse()
    }
}
