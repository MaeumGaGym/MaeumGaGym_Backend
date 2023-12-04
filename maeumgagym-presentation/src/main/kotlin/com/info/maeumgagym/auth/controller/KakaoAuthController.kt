package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotBlank

@Validated
@RequestMapping("/kakao")
@RestController
class KakaoAuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): TokenResponse {
        return kakaoLoginUseCase.login(accessToken!!)
    }
}
