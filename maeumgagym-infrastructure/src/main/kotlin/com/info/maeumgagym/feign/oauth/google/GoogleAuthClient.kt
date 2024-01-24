package com.info.maeumgagym.feign.oauth.google

import com.info.maeumgagym.global.config.feign.FeignConfig
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
