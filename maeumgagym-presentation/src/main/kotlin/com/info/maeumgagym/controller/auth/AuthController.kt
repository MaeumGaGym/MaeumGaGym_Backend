package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.request.ReissueWebRequest
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
import com.info.maeumgagym.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.auth.port.`in`.ReissueUseCase
import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
    fun duplicatedNicknameCheck(@RequestParam("nickname", required = true) nickname: String): Boolean =
        duplicatedNicknameCheckUseCase.existByNickname(nickname)

    @Operation(summary = "JWT 재발급 API")
    @PostMapping("/re-issue")
    fun reissue(
        @RequestBody @Valid
        req: ReissueWebRequest
    ): TokenWebResponse =
        TokenWebResponse.toWebResponse(reissueUseCase.reissue(req.refreshToken!!))
}
