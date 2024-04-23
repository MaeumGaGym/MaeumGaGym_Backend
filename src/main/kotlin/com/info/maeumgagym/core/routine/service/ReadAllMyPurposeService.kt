package com.info.maeumgagym.core.routine.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.MaeumGaGymException

@ReadOnlyUseCase
class ReadAllMyPurposeService(
    private val readPurposePort: com.info.maeumgagym.core.purpose.port.out.ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : com.info.maeumgagym.core.purpose.port.`in`.ReadAllMyPurposeUseCase {

    override fun readAllMyPurpose(index: Int): com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val purposes = readPurposePort.readByUserIdPaged(user.id!!, index)

        if (purposes.isEmpty()) {
            throw MaeumGaGymException.NO_CONTENT
        }

        return com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse(
            purposes.map {
                it.toResponse()
            }
        )
    }
}
