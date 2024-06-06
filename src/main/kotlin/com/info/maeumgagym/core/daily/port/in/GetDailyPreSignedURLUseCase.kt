package com.info.maeumgagym.core.daily.port.`in`

import com.info.maeumgagym.core.daily.dto.response.PreSignedURLResponse

interface GetDailyPreSignedURLUseCase {

    fun getUploadUrl(title: String): PreSignedURLResponse
}
