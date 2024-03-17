package com.info.maeumgagym.external.feign.oauth.apple

import com.info.maeumgagym.config.feign.FeignConfig
import com.info.maeumgagym.external.feign.oauth.apple.dto.ApplePublicKeysFeignResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "AppleClient",
    url = "https://appleid.apple.com/auth",
    configuration = [FeignConfig::class]
)
interface AppleClient {

    @GetMapping("/keys")
    fun applePublicKeys(): ApplePublicKeysFeignResponse
}
