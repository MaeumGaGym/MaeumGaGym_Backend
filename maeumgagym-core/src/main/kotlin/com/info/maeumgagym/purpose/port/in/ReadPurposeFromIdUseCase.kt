package com.info.maeumgagym.purpose.port.`in`

import com.info.maeumgagym.purpose.dto.response.PurposeInfoResponse

interface ReadPurposeFromIdUseCase {
    fun readPurposeFromId(id: Long): PurposeInfoResponse
}
