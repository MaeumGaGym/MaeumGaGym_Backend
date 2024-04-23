package com.info.maeumgagym.core.purpose.port.`in`

import com.info.maeumgagym.core.purpose.dto.response.PurposeInfoResponse

interface ReadPurposeFromIdUseCase {
    fun readPurposeFromId(id: Long): PurposeInfoResponse
}
