package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.dto.request.ReissueRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.auth.port.`in`.ReissueUseCase
import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import org.springframework.web.bind.annotation.*

@RequestMapping("/auth")
@WebAdapter
class AuthController(
    private val withdrawalUserUseCase: WithdrawalUserUseCase,
    private val duplicatedNicknameCheckUseCase: DuplicatedNicknameCheckUseCase,
    private val reissueUseCase: ReissueUseCase
) {
    @DeleteMapping
    fun userWithdrawal() {
        withdrawalUserUseCase.withdrawal()
    }

    @GetMapping
    fun duplicatedNicknameCheck(@RequestParam("nickname", required = true) nickname: String): Boolean =
        duplicatedNicknameCheckUseCase.existByNickname(nickname)

    @PostMapping("/reissue")
    fun reissue(@RequestBody request: ReissueRequest): TokenResponse =
        reissueUseCase.reissue(request)
}
