package com.info.maeumgagym.controller.auth

import com.info.common.responsibility.WebAdapter
import com.info.maeumgagym.auth.port.`in`.KakaoLoginUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.KakaoSignupUseCase
import com.info.maeumgagym.auth.port.out.KakaoGenerateTokenUseCase
import com.info.maeumgagym.presentation.controller.auth.dto.KakaoSignupWebRequest
import com.info.maeumgagym.presentation.controller.common.locationheader.LocationHeaderManager
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
import javax.validation.constraints.NotBlank

@Tag(name = "Kakao OAuth API")
@Validated
@RequestMapping("/kakao")
@WebAdapter
class KakaoAuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val kakaoSignupUseCase: KakaoSignupUseCase,
    private val kakaoRecoveryUseCase: KakaoRecoveryUseCase,
    private val locationHeaderManager: com.info.maeumgagym.presentation.controller.common.locationheader.LocationHeaderManager,
    private val kakaoGenerateTokenUseCase: KakaoGenerateTokenUseCase
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
                schema = Schema(type = "string", example = "RF-TOKEN=...; Secure; HttpOnly; SameSite=strict")
            )
        ]
    )
    @GetMapping("/login")
    fun login(
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        @RequestHeader("oauth-token", required = false)
        token: String?
    ): ResponseEntity<Any> =
        kakaoLoginUseCase.login(token!!).run {
            val responseHeaders = HttpHeaders().apply {
                add(HttpHeaders.SET_COOKIE, "RF-TOKEN=$second; Secure; HttpOnly; SameSite=strict")
                setBearerAuth(first)
            }

            ResponseEntity.ok().headers(responseHeaders).build()
        }

    @Operation(summary = "카카오 OAuth 회원가입 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        @RequestHeader("oauth-token", required = false)
        token: String?,
        @RequestBody
        @Valid
        req: com.info.maeumgagym.presentation.controller.auth.dto.KakaoSignupWebRequest
    ) {
        kakaoSignupUseCase.signup(token!!, req.nickname!!)

        locationHeaderManager.setURI("/kakao/login")
    }

    @Operation(summary = "카카오 OAuth 회원복구 API")
    @PutMapping("/recovery")
    fun recovery(
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        @RequestHeader("oauth-token", required = false)
        token: String?
    ) {
        kakaoRecoveryUseCase.recovery(token!!)
    }

    @Operation(summary = "카카오 토큰 발급 API")
    @ApiResponse(
        responseCode = "200",
        headers = [
            Header(
                name = "Set-Cookie",
                schema = Schema(type = "string", example = "OAUTH-TOKEN=...; Secure; HttpOnly; SameSite=strict")
            )
        ]
    )
    @GetMapping("/token/{code}")
    fun generateToken(
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        @PathVariable("code", required = false)
        code: String?
    ): ResponseEntity<Any> = ResponseEntity.ok().headers(
        HttpHeaders().apply {
            val token = kakaoGenerateTokenUseCase.generate(code!!)

            add(HttpHeaders.SET_COOKIE, "OAUTH-TOKEN=$token; Secure; HttpOnly; SameSite=strict")
        }
    ).build()
}
