package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.feign.file.FileClient
import com.info.maeumgagym.global.env.file.FileProperty
import com.info.maeumgagym.pickle.dto.request.PreSignedUploadURLRequest
import com.info.maeumgagym.pickle.port.out.GetPreSignedURLPort
import org.springframework.stereotype.Component

@Component
class FileAdapter(
    private val fileClient: FileClient,
    private val fileProperty: FileProperty
) : GetPreSignedURLPort {

    override fun getPreSignedUploadURL(fileType: String) =
        fileClient.preSignedUploadUrl(
            fileProperty.secretKey,
            PreSignedUploadURLRequest(fileType)
        ).uploadURL
}
