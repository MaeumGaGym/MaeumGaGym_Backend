package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.exception.FileTypeMissMatchedException
import com.info.maeumgagym.pickle.port.`in`.GetPreSignedUploadURLUseCase
import com.info.maeumgagym.pickle.port.out.GetPreSignedURLPort

@UseCase
class GetPreSignedURLService(
    private val getPreSignedURLPort: GetPreSignedURLPort
) : GetPreSignedUploadURLUseCase {

    private companion object {
        const val QUICKTIME = "video/quicktime"
        const val MP4 = "video/mp4"
    }

    override fun getPreSignedUploadURL(fileType: String): String {
        // WHEN : 확인 되지 않은 파일 타입 -> Exception
        if (fileType != QUICKTIME && fileType != MP4) throw FileTypeMissMatchedException

        // WHAT : Feign으로 PreSignedURL 불러오기
        return getPreSignedURLPort.getPreSignedUploadURL(fileType)
    }
}
