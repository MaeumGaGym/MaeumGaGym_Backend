package com.info.maeumgagym.controller.user

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.user.dto.UpdateUserInfoWebRequest
import com.info.maeumgagym.user.port.`in`.UpdateUserInfoUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Tag(name = "User API")
@RequestMapping("/user")
@WebAdapter
@Validated
class UserController(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) {
    @Operation(summary = "유저 정보 수정 API")
    @PutMapping
    fun updateUserInfo(
        @RequestBody @Valid
        updateUserInfoWebRequest: UpdateUserInfoWebRequest
    ) {
        updateUserInfoUseCase.update(updateUserInfoWebRequest.toRequest())
    }
}
