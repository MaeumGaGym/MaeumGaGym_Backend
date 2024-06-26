package com.info.maeumgagym.presentation.controller.auth

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.Permitted
import com.info.maeumgagym.core.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.core.auth.port.`in`.AppleRecoveryUseCase
import com.info.maeumgagym.core.auth.port.`in`.AppleSignUpUseCase
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.auth.dto.SignupWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Tag(name = "Apple OAuth API")
@RequestMapping("/apple")
@WebAdapter
private class AppleAuthController(
    private val appleLoginUseCase: AppleLoginUseCase,
    private val appleRecoveryUseCase: AppleRecoveryUseCase,
    private val appleSignUpUseCase: AppleSignUpUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {

    @Operation(summary = "애플 OAuth 로그인 API")
    @ApiResponse(
        responseCode = "200",
        headers = [
            Header(
                name = "Authorization",
                schema = Schema(type = "string", example = "(Prefix) ...")
            ),
            Header(
                name = "Set-Cookie",
                schema = Schema(type = "string", example = "RF-TOKEN=...; Secure; HttpOnly; SameSite=strict")
            )
        ]
    )
    @Permitted
    @GetMapping("/login")
    fun login(
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        @RequestHeader("oauth-token", required = false)
        token: String?
    ): ResponseEntity<Any> =
        appleLoginUseCase.login(token!!).run {
            val responseHeaders = HttpHeaders().apply {
                add(HttpHeaders.SET_COOKIE, "RF-TOKEN=$second; Secure; HttpOnly; SameSite=strict")
                set(HttpHeaders.AUTHORIZATION, first)
            }

            ResponseEntity.ok().headers(responseHeaders).build()
        }

    @Operation(summary = "애플 OAuth 회원가입 API")
    @ResponseStatus(HttpStatus.CREATED)
    @Permitted
    @PostMapping("/signup")
    fun signup(
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        @RequestHeader("oauth-token", required = false)
        token: String?,
        @RequestBody
        @Valid
        req: SignupWebRequest
    ) {
        appleSignUpUseCase.signUp(token!!, req.nickname!!)

        locationHeaderManager.setURI("/apple/login")
    }

    @Operation(summary = "애플 OAuth 회원복구 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Permitted
    @PutMapping("/recovery")
    fun recovery(
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        @RequestHeader("oauth-token", required = false)
        token: String?
    ) {
        appleRecoveryUseCase.recovery(token!!)
    }
}
