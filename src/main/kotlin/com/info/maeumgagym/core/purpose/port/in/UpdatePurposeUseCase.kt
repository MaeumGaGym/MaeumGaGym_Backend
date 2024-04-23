package com.info.maeumgagym.core.purpose.port.`in`

import com.info.maeumgagym.core.purpose.dto.request.UpdatePurposeRequest

interface UpdatePurposeUseCase {
    fun updatePurposeFromId(id: Long, req: com.info.maeumgagym.core.purpose.dto.request.UpdatePurposeRequest)
}
