package com.info.maeumgagym.feign.oauth.kakao

import com.info.maeumgagym.global.config.feign.FeignConfig
import com.info.maeumgagym.auth.dto.response.KakaoTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "KakaoAuthClient",
    url = "https://kauth.kakao.com",
    configuration = [FeignConfig::class]
)
interface KakaoAuthClient {
    @PostMapping("/oauth/token")
    fun kakaoAuth(
        @RequestParam(name = "grant_type") grantType: String,
        @RequestParam(name = "client_id") clientId: String,
        @RequestParam(name = "redirect_uri") redirectUri: String,
        @RequestParam(name = "code") code: String
    ): KakaoTokenResponse
}
