package com.info.maeumgagym.auth.oauth.apple.feign

import com.info.maeumgagym.auth.oauth.apple.feign.dto.ApplePublicKeysFeignResponse
import com.info.maeumgagym.feign.config.FeignConfig
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
