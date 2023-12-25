package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PreSignedUrlResponse

interface GetPreSignedUrlUseCase {

    fun getPreSignedUrl(fileType: String): PreSignedUrlResponse
}
