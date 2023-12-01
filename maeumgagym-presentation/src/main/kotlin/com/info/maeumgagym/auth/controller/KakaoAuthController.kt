package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.GetKakaoAccessTokenUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
@RequestMapping("/kakao")
@RestController
class KakaoAuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) {
    @PostMapping("/login")
    fun login(@RequestParam("accessToken", required = true) @NotBlank accessToken: String?): TokenResponse {
        return kakaoLoginUseCase.login(accessToken!!)
    }
}
