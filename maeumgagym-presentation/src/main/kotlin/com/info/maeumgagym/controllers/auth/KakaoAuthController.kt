package com.info.maeumgagym.controllers.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controllers.auth.dto.request.KakaoSignupRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotBlank

@Validated
@RequestMapping("/kakao")
@WebAdapter
class KakaoAuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val kakaoSignupUseCase: KakaoSignupUseCase
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): TokenResponse =
        kakaoLoginUseCase.login(accessToken!!)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestParam("access_token", required = true) @NotBlank accessToken: String?,
        @RequestBody kakaoSignupRequest: KakaoSignupRequest
    ) {
        kakaoSignupUseCase.signup(accessToken!!, kakaoSignupRequest.nickname)
    }
}
