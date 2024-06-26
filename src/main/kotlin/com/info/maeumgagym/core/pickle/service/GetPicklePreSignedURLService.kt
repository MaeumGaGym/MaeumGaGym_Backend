package com.info.maeumgagym.core.pickle.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.pickle.dto.response.PreSignedUploadURLResponse
import com.info.maeumgagym.core.pickle.model.VideoIdAndUploaderId
import com.info.maeumgagym.core.pickle.port.`in`.GetPicklePreSignedURLUseCase
import com.info.maeumgagym.core.pickle.port.out.GetPreSignedVideoUploadURLPort
import com.info.maeumgagym.core.pickle.port.out.SaveVideoIdAndUploaderIdPort

@ReadOnlyUseCase
internal class GetPicklePreSignedURLService(
    private val getPreSignedVideoUploadURLPort: GetPreSignedVideoUploadURLPort,
    private val saveVideoIdAndUploaderIdPort: SaveVideoIdAndUploaderIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : GetPicklePreSignedURLUseCase {

    private companion object {
        const val QUICKTIME = "video/quicktime"
        const val MP4 = "video/mp4"
    }

    override fun getUploadURL(fileType: String): PreSignedUploadURLResponse {
        if (fileType != QUICKTIME && fileType != MP4) throw BusinessLogicException(400, "File Type Mismatched")

        // WHAT : Feign으로 PreSignedURL 불러오기
        val preSignedUploadURLDto = getPreSignedVideoUploadURLPort.getPreSignedURL(fileType)

        // video id 저장
        saveVideoIdAndUploaderIdPort.save(
            VideoIdAndUploaderId(
                preSignedUploadURLDto.videoId,
                readCurrentUserPort.readCurrentUser().id!!
            )
        )

        return PreSignedUploadURLResponse(preSignedUploadURLDto.url)
    }
}
