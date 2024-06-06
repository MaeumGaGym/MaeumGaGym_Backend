package com.info.maeumgagym.infrastructure.external.feign.oauth.apple

import com.info.maeumgagym.infrastructure.config.feign.FeignConfig
import com.info.maeumgagym.infrastructure.external.feign.oauth.apple.dto.ApplePublicKeysFeignResponse
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
