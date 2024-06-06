package com.info.maeumgagym.core.daily.port.`in`

import com.info.maeumgagym.common.dto.LocationSubjectDto

interface CreateDailyUseCase {

    fun create(title: String): LocationSubjectDto
}
