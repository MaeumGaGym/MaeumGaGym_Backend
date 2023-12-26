package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.request.KakaoSignupWebRequest
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
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
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): TokenWebResponse =
        TokenWebResponse.toWebResponse(kakaoLoginUseCase.login(accessToken!!))

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestParam("access_token", required = true) @NotBlank accessToken: String?,
        @RequestBody req: KakaoSignupWebRequest
    ) {
        kakaoSignupUseCase.signup(accessToken!!, req.nickname!!)
    }
}
