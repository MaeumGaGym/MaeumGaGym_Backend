package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.feign.file.FileClient
import com.info.maeumgagym.feign.file.dto.request.PreSignedUploadURLFeignRequest
import com.info.maeumgagym.global.env.file.FileProperty
import com.info.maeumgagym.pickle.port.out.ExternalDeletePicklePort
import com.info.maeumgagym.pickle.port.out.ExternalGenerateUploadURLPort
import org.springframework.stereotype.Component

@Component
internal class FileAdapter(
    private val fileClient: FileClient,
    private val fileProperty: FileProperty
) : ExternalDeletePicklePort, ExternalGenerateUploadURLPort {

    override fun getPreSignedURL(fileType: String): String =
        fileClient.preSignedUploadURL(
            fileProperty.secretKey,
            PreSignedUploadURLFeignRequest(fileType)
        ).uploadURL

    override fun generateURL(videoId: String): String =
        fileProperty.serverURL + videoId + fileProperty.suffixPath

    override fun delete(videoId: String) {
        fileClient.pickleDelete(
            fileProperty.secretKey,
            videoId
        )
    }
}
