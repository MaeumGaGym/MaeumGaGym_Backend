package com.info.maeumgagym.daily.port.`in`

import com.info.maeumgagym.daily.dto.response.PreSignedURLResponse

interface GetDailyPreSignedURLUseCase {

    fun getUploadUrl(title: String): PreSignedURLResponse
}
