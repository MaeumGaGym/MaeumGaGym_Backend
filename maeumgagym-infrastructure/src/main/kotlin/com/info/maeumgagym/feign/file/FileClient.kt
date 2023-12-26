package com.info.maeumgagym.feign.file

import com.info.maeumgagym.feign.file.dto.response.FilePreSignedUploadURLFeignResponse
import com.info.maeumgagym.global.config.feign.FeignConfig
import com.info.maeumgagym.feign.file.dto.request.PreSignedUploadURLFeignRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "FileClient",
    url = "\${file.url}",
    configuration = [FeignConfig::class]
)
interface FileClient {

    @PostMapping
    fun preSignedUploadURL(
        @RequestHeader(name = "MaeumgaGym-Token")
        secretToken: String,
        @RequestBody
        req: PreSignedUploadURLFeignRequest
    ): FilePreSignedUploadURLFeignResponse
}
