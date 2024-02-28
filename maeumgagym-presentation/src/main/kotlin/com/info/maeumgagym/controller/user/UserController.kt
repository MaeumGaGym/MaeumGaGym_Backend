package com.info.maeumgagym.controller.user

import com.info.common.WebAdapter
import com.info.maeumgagym.user.dto.response.UserProfileResponse
import com.info.maeumgagym.user.port.`in`.ReadUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.constraints.NotBlank

@Tag(name = "User API")
@Validated
@WebAdapter
@RequestMapping("/user")
class UserController(
    private val readUserUseCase: ReadUserUseCase
) {

    @Operation(summary = "프로필 보기 API")
    @GetMapping("/{nickname}")
    fun getProfile(
        @PathVariable(name = "nickname")
        @NotBlank(message = "null일 수 없습니다.")
        nickname: String?
    ): UserProfileResponse = readUserUseCase.profileFromNickname(nickname!!)
}
