package com.info.maeumgagym.core.purpose.port.`in`

import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.purpose.dto.request.CreatePurposeRequest

interface CreatePurposeUseCase {
    fun createPurpose(req: CreatePurposeRequest): LocationSubjectDto
}
