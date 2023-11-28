package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.GetKakaoAccessTokenUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class KakaoAuthController(
    private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
    private val kakaoLoginUseCase: KakaoLoginUseCase
) {
    @GetMapping("/app/login/kakao")
    fun login(@RequestParam("code") code: String): TokenResponse {
        val kakaoTokenResponse = getKakaoAccessTokenUseCase.getAccessToken(code)
        return kakaoLoginUseCase.login(kakaoTokenResponse.accessToken)
    }
}
