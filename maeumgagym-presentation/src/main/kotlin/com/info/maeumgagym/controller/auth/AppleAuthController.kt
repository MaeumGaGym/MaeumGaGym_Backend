package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.AppleRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.AppleSignUpUseCase
import com.info.maeumgagym.controller.auth.dto.SignupWebRequest
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

@Tag(name = "Apple OAuth API")
@RequestMapping("/apple")
@WebAdapter
class AppleAuthController(
    private val appleLoginUseCase: AppleLoginUseCase,
    private val appleRecoveryUseCase: AppleRecoveryUseCase,
    private val appleSignUpUseCase: AppleSignUpUseCase
) {

    @Operation(summary = "애플 OAuth 로그인 API")
    @ApiResponse(
        responseCode = "200",
        headers = [
            Header(
                name = "Authorization",
                schema = Schema(type = "string", example = "Bearer ...")
            ),
            Header(
                name = "Set-Cookie",
                schema = Schema(type = "string", example = "RF-TOKEN=...; Secure; HttpOnly; SameSite=Strict")
            )
        ]
    )
    @GetMapping("/login")
    fun login(@RequestParam("access_token") token: String): ResponseEntity<Any> =
        appleLoginUseCase.login(token).run {
            val responseHeaders = HttpHeaders().apply {
                add(HttpHeaders.SET_COOKIE, "RF-TOKEN=$second; Secure; HttpOnly; SameSite=Strict")
                setBearerAuth(first)
            }

            ResponseEntity.ok().headers(responseHeaders).build()
        }

    @Operation(summary = "애플 OAuth 회원가입 API")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestParam("access_token")
        token: String,
        @RequestBody
        @Valid
        req: SignupWebRequest
    ) { appleSignUpUseCase.signUp(token, req.nickname!!) }

    @Operation(summary = "애플 OAuth 회원복구 API")
    @PutMapping("/recovery")
    fun recovery(@RequestParam("access_token") token: String) {
        appleRecoveryUseCase.recovery(token)
    }
}
