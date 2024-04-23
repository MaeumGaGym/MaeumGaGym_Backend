package com.info.maeumgagym.core.purpose.port.`in`

import com.info.maeumgagym.core.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.purpose.dto.request.CreatePurposeRequest

interface CreatePurposeUseCase {
    fun createPurpose(req: com.info.maeumgagym.core.purpose.dto.request.CreatePurposeRequest): LocationSubjectDto
}
