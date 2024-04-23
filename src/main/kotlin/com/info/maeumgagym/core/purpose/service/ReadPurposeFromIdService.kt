package com.info.maeumgagym.core.purpose.service

import com.info.maeumgagym.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.common.exception.SecurityException
import com.info.maeumgagym.core.purpose.dto.response.PurposeInfoResponse
import com.info.maeumgagym.core.purpose.port.`in`.ReadPurposeFromIdUseCase
import com.info.maeumgagym.core.purpose.port.out.ReadPurposePort

@ReadOnlyUseCase
class ReadPurposeFromIdService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPurposePort: ReadPurposePort
) : ReadPurposeFromIdUseCase {

    override fun readPurposeFromId(id: Long): PurposeInfoResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose = readPurposePort.readById(id) ?: throw BusinessLogicException.PURPOSE_NOT_FOUND

        if (purpose.user.id != user.id) throw SecurityException.PERMISSION_DENIED

        return purpose.toResponse()
    }
}
