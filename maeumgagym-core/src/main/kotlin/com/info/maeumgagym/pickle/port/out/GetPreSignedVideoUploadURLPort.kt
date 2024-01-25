package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.dto.PreSignedUploadURLDto

interface GetPreSignedVideoUploadURLPort {

    fun getPreSignedURL(fileType: String): PreSignedUploadURLDto
}
