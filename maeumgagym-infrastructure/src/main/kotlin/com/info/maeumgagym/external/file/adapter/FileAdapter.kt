package com.info.maeumgagym.external.file.adapter

import com.info.maeumgagym.common.exception.FeignException
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.env.file.FileProperties
import com.info.maeumgagym.external.feign.FileClient
import com.info.maeumgagym.external.feign.dto.request.PreSignedUploadURLFeignRequest
import com.info.maeumgagym.pickle.dto.PreSignedUploadURLDto
import com.info.maeumgagym.pickle.port.out.DeleteOriginalVideoPort
import com.info.maeumgagym.pickle.port.out.GenerateM3u8URLPort
import com.info.maeumgagym.pickle.port.out.GetPreSignedVideoUploadURLPort
import org.springframework.stereotype.Component

@Component
internal class FileAdapter(
    private val fileClient: FileClient,
    private val fileProperty: FileProperties
) : DeleteOriginalVideoPort, GenerateM3u8URLPort, GetPreSignedVideoUploadURLPort {

    override fun getPreSignedURL(fileType: String): PreSignedUploadURLDto = try {
        fileClient.preSignedUploadURLForVideo(
            fileProperty.secretKey,
            PreSignedUploadURLFeignRequest(fileType)
        ).run {
            PreSignedUploadURLDto(uploadURL, videoId)
        }
    } catch (e: FeignException) {
        throw MaeumGaGymException.INTERNAL_SERVER_ERROR
    }

    override fun generateURL(videoId: String): String =
        fileProperty.serverURL + videoId + fileProperty.suffixPath

    override fun callDeleteAPIOnExternal(videoId: String) {
        fileClient.pickleDelete(
            fileProperty.secretKey,
            videoId
        )
    }
}
