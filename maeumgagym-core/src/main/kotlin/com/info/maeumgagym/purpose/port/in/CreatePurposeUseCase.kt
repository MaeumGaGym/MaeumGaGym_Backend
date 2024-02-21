package com.info.maeumgagym.purpose.port.`in`

import com.info.maeumgagym.purpose.dto.request.CreatePurposeRequest

interface CreatePurposeUseCase {
    fun createPurpose(req: CreatePurposeRequest)
}
