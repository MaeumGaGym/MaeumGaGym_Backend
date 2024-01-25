package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.ReissueWebRequest
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
    @GetMapping
    fun duplicatedNicknameCheck(@RequestParam("nickname") nickname: String): Boolean =
        duplicatedNicknameCheckUseCase.existByNickname(nickname)

    @Operation(summary = "JWT 재발급 API")
    @GetMapping("/re-issue")
    fun reissue(
        @RequestBody @Valid
        req: ReissueWebRequest
    ): ResponseEntity<Any> =
        reissueUseCase.reissue(req.refreshToken!!).run {
            val responseHeaders = HttpHeaders().apply {
                add(HttpHeaders.SET_COOKIE, "RF-TOKEN=$second; Secure; HttpOnly; SameSite=Strict")
                add(HttpHeaders.AUTHORIZATION, "Bearer $first")
            }

            ResponseEntity.ok().headers(responseHeaders).build()
        }
}
