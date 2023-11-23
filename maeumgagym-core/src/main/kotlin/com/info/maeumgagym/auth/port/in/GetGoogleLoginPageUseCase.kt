package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.response.GoogleLinkResponse

interface GetGoogleLoginPageUseCase {

    fun getGoogleLoginPage(): GoogleLinkResponse
}
