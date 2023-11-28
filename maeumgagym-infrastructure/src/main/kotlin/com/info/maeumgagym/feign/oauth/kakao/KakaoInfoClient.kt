package com.info.maeumgagym.feign.oauth.kakao

import com.info.maeumgagym.global.config.feign.FeignConfig
import com.info.maeumgagym.auth.dto.response.KakaoInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "KakaoInfoClient",
    url = "https://kapi.kakao.com",
    configuration = [FeignConfig::class]
)
interface KakaoInfoClient {
    @PostMapping("/v2/user/me")
    fun kakaoInfo(@RequestHeader("Authorization") accessToken: String): KakaoInfoResponse
}
