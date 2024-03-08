package com.info.maeumgagym.auth.oauth.google.feign

import com.info.maeumgagym.feign.config.FeignConfig
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
