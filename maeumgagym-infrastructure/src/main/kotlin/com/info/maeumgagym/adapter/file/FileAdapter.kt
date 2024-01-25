package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.feign.file.FileClient
import com.info.maeumgagym.feign.file.dto.request.PreSignedUploadURLFeignRequest
import com.info.maeumgagym.global.env.file.FileProperties
import com.info.maeumgagym.pickle.port.out.DeleteOriginalVideoPort
import com.info.maeumgagym.pickle.port.out.GenerateM3u8URLPort
import com.info.maeumgagym.pickle.port.out.GetPreSignedVideoUploadURLPort
import org.springframework.stereotype.Component

@Component
internal class FileAdapter(
    private val fileClient: FileClient,
    private val fileProperty: FileProperties
) : DeleteOriginalVideoPort, GenerateM3u8URLPort, GetPreSignedVideoUploadURLPort {

    override fun getPreSignedURL(fileType: String): String =
        fileClient.preSignedUploadURL(
            fileProperty.secretKey,
            PreSignedUploadURLFeignRequest(fileType)
        ).uploadURL

    override fun generateURL(videoId: String): String =
        fileProperty.serverURL + videoId + fileProperty.suffixPath

    override fun callDeleteAPIOnExternal(videoId: String) {
        fileClient.pickleDelete(
            fileProperty.secretKey,
            videoId
        )
    }
}
