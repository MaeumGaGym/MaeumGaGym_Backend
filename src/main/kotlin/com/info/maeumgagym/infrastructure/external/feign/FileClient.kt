package com.info.maeumgagym.infrastructure.external.feign

import com.info.maeumgagym.infrastructure.config.feign.FeignConfig
import com.info.maeumgagym.infrastructure.external.feign.dto.request.PreSignedUploadURLFeignRequest
import com.info.maeumgagym.infrastructure.external.feign.dto.response.FilePreSignedUploadURLFeignResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "FileClient",
    url = "\${file.server-url}",
    configuration = [FeignConfig::class]
)
interface FileClient {

    @PostMapping("/generate", produces = ["application/json"])
    fun preSignedUploadURLForVideo(
        @RequestHeader(name = "MaeumgaGym-Token")
        secretToken: String,
        @RequestBody
        req: PreSignedUploadURLFeignRequest
    ): FilePreSignedUploadURLFeignResponse

    @DeleteMapping("/{videoId}")
    fun pickleDelete(
        @RequestHeader(name = "MaeumgaGym-Token")
        secretToken: String,
        @PathVariable
        videoId: String
    )
}
