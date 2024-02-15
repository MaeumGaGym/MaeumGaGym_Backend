package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.KakaoSignupWebRequest
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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
    @ApiResponse(
        responseCode = "200",
        headers = [
            Header(
                name = "Authorization",
                schema = Schema(type = "string", example = "Bearer ...")
            ),
            Header(
                name = "Set-Cookie",
                schema = Schema(type = "string", example = "RF-TOKEN=...; Secure; HttpOnly; SameSite=lax")
            )
        ]
    )
    @GetMapping("/login")
    fun login(@RequestParam("access_token") token: String): ResponseEntity<Any> =
        kakaoLoginUseCase.login(token).run {
            val responseHeaders = HttpHeaders().apply {
                add(HttpHeaders.SET_COOKIE, "RF-TOKEN=$second; Secure; HttpOnly; SameSite=lax")
                setBearerAuth(first)
            }

            ResponseEntity.ok().headers(responseHeaders).build()
        }

    @Operation(summary = "카카오 OAuth 회원가입 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestParam("access_token")
        accessToken: String,
        @RequestBody
        @Valid
        req: KakaoSignupWebRequest
    ) {
        kakaoSignupUseCase.signup(accessToken, req.nickname!!)
    }

    @Operation(summary = "카카오 OAuth 회원복구 API")
    @PutMapping("/recovery")
    fun recovery(@RequestParam("access_token") accessToken: String) {
        kakaoRecoveryUseCase.recovery(accessToken)
    }
}
