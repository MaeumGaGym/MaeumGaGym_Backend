package com.info.maeumgagym.daily.port.`in`

import com.info.maeumgagym.common.dto.LocationSubjectDto

interface CreateDailyUseCase {

    fun create(title: String): LocationSubjectDto
}
