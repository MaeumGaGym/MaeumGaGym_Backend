package com.info.maeumgagym.controller.user.dto

import com.info.maeumgagym.user.dto.response.UserResponse

data class UserWebResponse(
    val nickname: String,
    val profileImage: String?
) {

    companion object {

        fun toWrbResponse(res: UserResponse) = UserWebResponse(
            res.nickname,
            res.profileImage
        )
    }
}
