package com.info.maeumgagym.infrastructure.external.feign.oauth.google

import com.info.maeumgagym.infrastructure.config.feign.FeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "GoogleAuthClient", url = "https://oauth2.googleapis.com", configuration = [FeignConfig::class])
interface GoogleAuthClient {

    @PostMapping("/revoke")
    fun revokeToken(
        @RequestParam(name = "token") token: String
    )
}
