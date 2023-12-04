package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val withdrawalUserUseCase: WithdrawalUserUseCase,
    private val duplicatedNicknameCheckUseCase: DuplicatedNicknameCheckUseCase
) {
    @DeleteMapping
    fun withdrawal() = withdrawalUserUseCase.withdrawal()

    @GetMapping
    fun duplicatedNicknameCheck(
        @RequestParam("nickname", required = true)
        nickname: String
    ) = duplicatedNicknameCheckUseCase.existByNickname(nickname)
}
