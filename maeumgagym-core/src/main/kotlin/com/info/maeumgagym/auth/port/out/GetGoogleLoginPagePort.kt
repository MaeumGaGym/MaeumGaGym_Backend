package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.GoogleLinkResponse

interface GetGoogleLoginPagePort {

    fun getGoogleLoginPage(): GoogleLinkResponse
}
