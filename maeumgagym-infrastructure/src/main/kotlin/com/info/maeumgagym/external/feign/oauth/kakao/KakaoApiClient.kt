package com.info.maeumgagym.external.feign.oauth.kakao

import com.info.maeumgagym.config.feign.FeignConfig
import com.info.maeumgagym.external.feign.oauth.kakao.dto.KakaoProfileFeignResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "KakaoApiClient",
    url = "https://kapi.kakao.com",
    configuration = [FeignConfig::class]
)
interface KakaoApiClient {
    @PostMapping("/v2/user/me")
    fun kakaoProfile(@RequestHeader("Authorization") accessToken: String): KakaoProfileFeignResponse

    @PostMapping("/v1/user/logout")
    fun revoke(@RequestHeader("Authorization") accessToken: String)
}
