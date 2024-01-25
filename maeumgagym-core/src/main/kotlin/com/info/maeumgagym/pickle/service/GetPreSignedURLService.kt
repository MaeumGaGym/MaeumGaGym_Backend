package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.response.PreSignedUploadURLResponse
import com.info.maeumgagym.pickle.exception.FileTypeMissMatchedException
import com.info.maeumgagym.pickle.model.VideoIdAndUploaderId
import com.info.maeumgagym.pickle.port.`in`.GetPreSignedUploadURLUseCase
import com.info.maeumgagym.pickle.port.out.GetPreSignedVideoUploadURLPort
import com.info.maeumgagym.pickle.port.out.SaveVideoIdAndUploaderIdPort

@UseCase
internal class GetPreSignedURLService(
    private val getPreSignedVideoUploadURLPort: GetPreSignedVideoUploadURLPort,
    private val saveVideoIdAndUploaderIdPort: SaveVideoIdAndUploaderIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : GetPreSignedUploadURLUseCase {

    private companion object {
        const val QUICKTIME = "video/quicktime"
        const val MP4 = "video/mp4"
    }

    override fun getPreSignedUploadURL(fileType: String): PreSignedUploadURLResponse {
        // WHEN : 확인 되지 않은 파일 타입 -> Exception
        if (fileType != QUICKTIME && fileType != MP4) throw FileTypeMissMatchedException

        val preSignedUploadURLDto = getPreSignedVideoUploadURLPort.getPreSignedURL(fileType)

        saveVideoIdAndUploaderIdPort.save(
            VideoIdAndUploaderId(
                preSignedUploadURLDto.videoId,
                readCurrentUserPort.readCurrentUser().id!!
            )
        )

        // WHAT : Feign으로 PreSignedURL 불러오기
        return PreSignedUploadURLResponse(preSignedUploadURLDto.url)
    }
}
