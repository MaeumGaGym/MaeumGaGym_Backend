package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.dto.PreSignedUploadURLDto

interface GetPreSignedVideoUploadURLPort {

    fun getPreSignedURL(fileType: String): PreSignedUploadURLDto
}
