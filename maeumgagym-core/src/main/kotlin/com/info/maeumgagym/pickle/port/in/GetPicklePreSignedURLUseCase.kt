package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PreSignedUploadURLResponse

interface GetPicklePreSignedURLUseCase {

    fun getUploadURL(fileType: String): PreSignedUploadURLResponse
}
