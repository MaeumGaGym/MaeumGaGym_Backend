package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PreSignedUploadURLResponse

interface GetPreSignedUploadURLUseCase {

    fun getPreSignedUploadURL(fileType: String): PreSignedUploadURLResponse
}
