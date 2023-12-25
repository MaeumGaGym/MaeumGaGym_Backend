package com.info.maeumgagym.feign.file

import com.info.maeumgagym.feign.file.dto.FilePreSignedUrlResponse
import com.info.maeumgagym.global.config.feign.FeignConfig
import com.info.maeumgagym.pickle.dto.request.PreSignedUrlRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "FileClient",
    url = "https://video-macos.pokabook.kr",
    configuration = [FeignConfig::class]
)
interface FileClient {

    @PostMapping
    fun preSignedUrl(
        @RequestHeader(name = "MaeumgaGym-Token")
        secretToken: String,
        @RequestBody
        req: PreSignedUrlRequest
    ): FilePreSignedUrlResponse
}
