package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.pickle.dto.request.CreatePickleRequest

interface CreatePickleUseCase {

    fun createPickle(req: CreatePickleRequest): LocationSubjectDto
}
