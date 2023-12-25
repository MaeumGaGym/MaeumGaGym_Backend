package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.dto.response.PreSignedUrlResponse

interface GetPreSignedUrlPort {

    fun getPreSignedUrl(fileType: String): PreSignedUrlResponse
}
