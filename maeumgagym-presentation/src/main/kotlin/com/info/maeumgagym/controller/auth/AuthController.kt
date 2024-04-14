package com.info.maeumgagym.controller.auth

import com.info.common.responsibility.WebAdapter
import com.info.maeumgagym.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.auth.port.`in`.ReissueUseCase
import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Tag(name = "Auth API")
@RequestMapping("/auth")
@WebAdapter
@Validated
class AuthController(
    private val withdrawalUserUseCase: WithdrawalUserUseCase,
    private val duplicatedNicknameCheckUseCase: DuplicatedNicknameCheckUseCase,
    private val reissueUseCase: ReissueUseCase
) {
    @Operation(summary = "회원탈퇴 API")
    @DeleteMapping
    fun userWithdrawal() {
        withdrawalUserUseCase.withdrawal()
    }

    @Operation(summary = "nickname 중복체크 API")
    @GetMapping("/nickname/{nickname}")
    fun duplicatedNicknameCheck(
        @PathVariable("nickname", required = false)
        @Valid
        @NotBlank(message = "null일 수 없습니다.")
        nickname: String?
    ): Boolean = duplicatedNicknameCheckUseCase.existByNickname(nickname!!)

    @Operation(summary = "JWT 재발급 API")
    @GetMapping("/re-issue")
    fun reissue(
        @Valid @NotBlank(message = "null일 수 없습니다.")
        @RequestHeader("RF-TOKEN")
        token: String?,
    ): ResponseEntity<Any> =
        reissueUseCase.reissue(token!!).run {
            val responseHeaders = HttpHeaders().apply {
                add(HttpHeaders.SET_COOKIE, "RF-TOKEN=$second; Secure; HttpOnly; SameSite=strict")
                setBearerAuth(first)
            }

            ResponseEntity.ok().headers(responseHeaders).build()
        }
}
