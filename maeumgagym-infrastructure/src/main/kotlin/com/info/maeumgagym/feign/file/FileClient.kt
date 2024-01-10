package com.info.maeumgagym.feign.file

import com.info.maeumgagym.feign.file.dto.request.PreSignedUploadURLFeignRequest
import com.info.maeumgagym.feign.file.dto.response.FilePreSignedUploadURLFeignResponse
import com.info.maeumgagym.global.config.feign.FeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "FileClient",
    url = "\${file.server-url}",
    configuration = [FeignConfig::class]
)
interface FileClient {

    @PostMapping("/generate", produces = ["application/json"])
    fun preSignedUploadURL(
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
