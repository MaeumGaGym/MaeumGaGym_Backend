package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.dto.response.PreSignedUrlResponse
import com.info.maeumgagym.pickle.exception.FileTypeMissMatchedException
import com.info.maeumgagym.pickle.port.`in`.GetPreSignedUrlUseCase
import com.info.maeumgagym.pickle.port.out.GetPreSignedUrlPort

@UseCase
class GetPreSignedUrlService(
    private val getPreSignedUrlPort: GetPreSignedUrlPort
) : GetPreSignedUrlUseCase {

    private companion object {
        const val QUICKTIME = "quicktime"
        const val MP4 = "mp4"
        const val FILETYPE_PREFIX = "video/"
    }

    override fun getPreSignedUrl(fileType: String): PreSignedUrlResponse {
        if (fileType != QUICKTIME && fileType != MP4) throw FileTypeMissMatchedException

        return getPreSignedUrlPort.getPreSignedUrl(FILETYPE_PREFIX + fileType)
    }
}
