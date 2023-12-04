package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.controller.dto.request.KakaoSignupRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.KakaoDuplicateCheckUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotBlank

@Validated
@RequestMapping("/kakao")
@RestController
class KakaoAuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val kakaoSignupUseCase: KakaoSignupUseCase,
    private val kakaoDuplicateCheckUseCase: KakaoDuplicateCheckUseCase
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): TokenResponse {
        return kakaoLoginUseCase.login(accessToken!!)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestParam("access_token", required = true) @NotBlank accessToken: String?,
        @RequestBody kakaoSignupRequest: KakaoSignupRequest
    ) = kakaoSignupUseCase.signup(
        accessToken!!,
        kakaoSignupRequest.nickname
    )

    @GetMapping
    fun duplicatCheck(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): Boolean =
        kakaoDuplicateCheckUseCase.duplicateCheck(accessToken!!)
}
