package com.info.maeumgagym.presentation.controller.user

import com.info.maeumgagym.common.responsibility.WebAdapter
import com.info.maeumgagym.core.user.dto.response.UserProfileResponse
import com.info.maeumgagym.core.user.port.`in`.ReadUserUseCase
import com.info.maeumgagym.core.user.port.`in`.UpdateUserInfoUseCase
import com.info.maeumgagym.presentation.controller.user.dto.UpdateUserInfoWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Tag(name = "User API")
@Validated
@WebAdapter
@RequestMapping("/user")
private class UserController(
    private val readUserUseCase: ReadUserUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) {

    @Operation(summary = "프로필 보기 API")
    @GetMapping("/{nickname}")
    fun getProfile(
        @PathVariable(name = "nickname")
        @NotBlank(message = "null일 수 없습니다.")
        nickname: String?
    ): UserProfileResponse = readUserUseCase.profileFromNickname(nickname!!)

    @Operation(summary = "유저 정보 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    fun updateUserInfo(
        @RequestBody @Valid
        updateUserInfoWebRequest: UpdateUserInfoWebRequest
    ) {
        updateUserInfoUseCase.update(updateUserInfoWebRequest.toRequest())
    }
}
