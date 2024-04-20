package com.info.maeumgagym.purpose.port.`in`

import com.info.maeumgagym.purpose.dto.response.PurposeListResponse

interface ReadAllMyPurposeUseCase {

    fun readAllMyPurpose(index: Int): PurposeListResponse
}
