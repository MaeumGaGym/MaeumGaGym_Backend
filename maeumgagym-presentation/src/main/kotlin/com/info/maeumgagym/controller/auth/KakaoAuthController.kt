package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.request.KakaoSignupWebRequest
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Tag(name = "Kakao OAuth API")
@Validated
@RequestMapping("/kakao")
@WebAdapter
class KakaoAuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val kakaoSignupUseCase: KakaoSignupUseCase,
    private val kakaoRecoveryUseCase: KakaoRecoveryUseCase
) {
    @Operation(summary = "카카오 OAuth 로그인 API")
    @GetMapping("/login")
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): TokenWebResponse =
        TokenWebResponse.toWebResponse(kakaoLoginUseCase.login(accessToken!!))

    @Operation(summary = "카카오 OAuth 회원가입 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestParam("access_token", required = true)
        @NotBlank
        accessToken: String?,
        @RequestBody
        @Valid
        req: KakaoSignupWebRequest
    ) {
        kakaoSignupUseCase.signup(accessToken!!, req.nickname!!)
    }

    @Operation(summary = "카카오 OAuth 회원복구 API")
    @PutMapping("/recovery")
    fun recovery(@RequestParam("access_token") accessToken: String) {
        kakaoRecoveryUseCase.recovery(accessToken)
    }
}
