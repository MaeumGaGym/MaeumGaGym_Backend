package com.info.maeumgagym.feign.oauth.apple

import com.example.onui.global.config.feign.FeignConfig
import com.info.maeumgagym.feign.oauth.apple.dto.ApplePublicKeys
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping


@FeignClient(
    name = "AppleClient",
    url = "https://appleid.apple.com/auth",
    configuration = [FeignConfig::class]
)
interface AppleClient {

    @GetMapping("/keys")
    fun applePublicKeys(): ApplePublicKeys
}
