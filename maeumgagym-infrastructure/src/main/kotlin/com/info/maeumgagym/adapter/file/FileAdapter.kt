package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.feign.file.FileClient
import com.info.maeumgagym.global.env.file.FileProperty
import com.info.maeumgagym.pickle.dto.request.PreSignedUrlRequest
import com.info.maeumgagym.pickle.dto.response.PreSignedUrlResponse
import com.info.maeumgagym.pickle.port.out.GetPreSignedUrlPort
import org.springframework.stereotype.Component

@Component
class FileAdapter(
    private val fileClient: FileClient,
    private val fileProperty: FileProperty
) : GetPreSignedUrlPort {

    override fun getPreSignedUrl(fileType: String) =
        PreSignedUrlResponse(
            fileClient.preSignedUrl(
                fileProperty.secretKey,
                PreSignedUrlRequest(fileType)
            ).uploadURL
        )
}
