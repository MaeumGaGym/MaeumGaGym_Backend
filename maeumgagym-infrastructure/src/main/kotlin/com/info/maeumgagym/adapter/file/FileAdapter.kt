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

    private companion object {
        var loadBalancingNum: Int = 0
    }

    override fun getPreSignedUploadURL(fileType: String) =
        fileClient.preSignedUploadURL(
            fileProperty.secretKey,
            PreSignedUploadURLFeignRequest(fileType)
        ).uploadURL

    override fun generateUploadURL(videoId: Long): String =
        getBalancedURL() + videoId + fileProperty.suffixPath


    override fun deletePickle(videoId: Long) {
        fileClient.pickleDelete(
            fileProperty.secretKey,
            videoId
        )
    }

    private fun getBalancedURL(): String {
        if (fileProperty.urls.size == loadBalancingNum)
            loadBalancingNum--
        return fileProperty.urls[loadBalancingNum++] + "/"
    }
}
