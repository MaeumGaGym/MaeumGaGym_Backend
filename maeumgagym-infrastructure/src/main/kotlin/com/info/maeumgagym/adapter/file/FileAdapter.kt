package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.feign.file.FileClient
import com.info.maeumgagym.feign.file.dto.request.PreSignedUploadURLFeignRequest
import com.info.maeumgagym.global.env.file.FileProperty
import com.info.maeumgagym.pickle.port.out.FeignDeletePicklePort
import com.info.maeumgagym.pickle.port.out.GenerateUploadURLPort
import com.info.maeumgagym.pickle.port.out.GetPreSignedURLPort
import org.springframework.stereotype.Component

@Component
class FileAdapter(
    private val fileClient: FileClient,
    private val fileProperty: FileProperty
) : GetPreSignedURLPort, FeignDeletePicklePort, GenerateUploadURLPort {

    override fun getPreSignedUploadURL(fileType: String): String =
        fileClient.preSignedUploadURL(
            fileProperty.secretKey,
            PreSignedUploadURLFeignRequest(fileType)
        ).uploadURL

    override fun generateURL(videoId: String): String =
        fileProperty.serverURL + videoId + fileProperty.suffixPath

    override fun deletePickle(videoId: String) {
        fileClient.pickleDelete(
            fileProperty.secretKey,
            videoId
        )
    }
}
