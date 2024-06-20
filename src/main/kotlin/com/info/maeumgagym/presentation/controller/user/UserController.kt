package com.info.maeumgagym.presentation.controller.user

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.core.user.dto.response.UserProfileResponse
import com.info.maeumgagym.core.user.port.`in`.ReadCurrentUserProfileUseCase
import com.info.maeumgagym.core.user.port.`in`.ReadUserProfileFromNicknameUseCase
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
    private val readUserProfileFromNicknameUseCase: ReadUserProfileFromNicknameUseCase,
    private val readCurrentUserProfileUseCase: ReadCurrentUserProfileUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) {

    @Operation(summary = "닉네임으로 프로필 보기 API")
    @RequireAuthentication
    @GetMapping("/{nickname}")
    fun readUserProfileFromNickname(
        @PathVariable(name = "nickname")
        @NotBlank(message = "null일 수 없습니다.")
        nickname: String?
    ): UserProfileResponse = readUserProfileFromNicknameUseCase.profileFromNickname(nickname!!)

    @Operation(summary = "현재 로그인한 유저 프로필 조회 API")
    @RequireAuthentication
    @GetMapping
    fun getCurrentUserProfile(): UserProfileResponse = readCurrentUserProfileUseCase.readCurrentUserProfile()

    @Operation(summary = "유저 정보 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @PutMapping
    fun updateUserInfo(
        @RequestBody @Valid
        updateUserInfoWebRequest: UpdateUserInfoWebRequest
    ) {
        updateUserInfoUseCase.update(updateUserInfoWebRequest.toRequest())
    }
}
