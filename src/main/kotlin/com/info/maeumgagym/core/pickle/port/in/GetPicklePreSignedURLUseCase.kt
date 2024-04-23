package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.pickle.dto.response.PreSignedUploadURLResponse

interface GetPicklePreSignedURLUseCase {

    fun getUploadURL(fileType: String): PreSignedUploadURLResponse
}
