package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.feign.file.FileClient
import com.info.maeumgagym.global.env.file.FileProperty
import com.info.maeumgagym.feign.file.dto.request.PreSignedUploadURLFeignRequest
import com.info.maeumgagym.pickle.port.out.FeignDeletePicklePort
import com.info.maeumgagym.pickle.port.out.GetPreSignedURLPort
import org.springframework.stereotype.Component

@Component
class FileAdapter(
    private val fileClient: FileClient,
    private val fileProperty: FileProperty
) : GetPreSignedURLPort, FeignDeletePicklePort {

    override fun getPreSignedUploadURL(fileType: String) =
        fileClient.preSignedUploadURL(
            fileProperty.secretKey,
            PreSignedUploadURLFeignRequest(fileType)
        ).uploadURL

    override fun deletePickle(videoId: Long) {
        fileClient.pickleDelete(
            fileProperty.secretKey,
            videoId
        )
    }
}
