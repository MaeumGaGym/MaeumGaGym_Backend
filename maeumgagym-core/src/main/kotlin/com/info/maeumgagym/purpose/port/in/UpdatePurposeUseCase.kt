package com.info.maeumgagym.purpose.port.`in`

import com.info.maeumgagym.purpose.dto.request.UpdatePurposeRequest

interface UpdatePurposeUseCase {
    fun updatePurpose(id: Long, req: UpdatePurposeRequest)
}
