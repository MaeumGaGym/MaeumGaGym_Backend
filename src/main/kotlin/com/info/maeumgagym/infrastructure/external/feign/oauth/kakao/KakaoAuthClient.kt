package com.info.maeumgagym.infrastructure.external.feign.oauth.kakao

import com.info.maeumgagym.infrastructure.config.feign.FeignConfig
import com.info.maeumgagym.infrastructure.external.feign.oauth.kakao.dto.KakaoAuthResponse
import feign.Headers
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
    @Headers("Content-Type:application/x-www-form-urlencoded")
    fun kakaoAuth(
        @RequestParam("client_id")
        clientId: String,
        @RequestParam("redirect_uri")
        redirectUri: String,
        @RequestParam("code")
        code: String,
        @RequestParam("client_secret")
        clientSecret: String,
        @RequestParam("grant_type")
        grantType: String = "authorization_code"
    ): KakaoAuthResponse
}
