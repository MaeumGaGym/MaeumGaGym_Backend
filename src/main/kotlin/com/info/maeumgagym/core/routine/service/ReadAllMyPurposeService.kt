package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse
import com.info.maeumgagym.core.purpose.port.`in`.ReadAllMyPurposeUseCase
import com.info.maeumgagym.core.purpose.port.out.ReadPurposePort

@ReadOnlyUseCase
class ReadAllMyPurposeService(
    private val readPurposePort: ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadAllMyPurposeUseCase {

    override fun readAllMyPurpose(index: Int): PurposeListResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val purposes = readPurposePort.readByUserIdPaged(user.id!!, index)

        if (purposes.isEmpty()) {
            throw MaeumGaGymException.NO_CONTENT
        }

        return PurposeListResponse(
            purposes.map {
                it.toResponse()
            }
        )
    }
}
