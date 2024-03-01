package com.info.maeumgagym.purpose.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.purpose.dto.response.PurposeInfoResponse
import com.info.maeumgagym.purpose.exception.PurposeNotFoundException
import com.info.maeumgagym.purpose.port.`in`.ReadPurposeFromIdUseCase
import com.info.maeumgagym.purpose.port.out.ReadPurposePort

@ReadOnlyUseCase
class ReadPurposeFromIdService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPurposePort: ReadPurposePort
) : ReadPurposeFromIdUseCase {

    override fun readPurposeFromId(id: Long): PurposeInfoResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose = readPurposePort.readById(id) ?: throw PurposeNotFoundException

        if (purpose.user.id != user.id) throw PermissionDeniedException

        return purpose.toResponse()
    }
}
