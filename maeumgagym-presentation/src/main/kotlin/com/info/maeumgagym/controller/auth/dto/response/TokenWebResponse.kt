package com.info.maeumgagym.controller.auth.dto.response

import com.info.maeumgagym.auth.dto.response.TokenResponse

data class TokenWebResponse(

    val accessToken: String,
    val refreshToken: String
) {

    companion object {
        fun toWebResponse(res: TokenResponse) = TokenWebResponse(
            res.accessToken,
            res.refreshToken
        )
    }
}
