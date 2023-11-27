package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.google.dto.response.GoogleTokenResponse

interface GetGoogleAccessTokenPort {

    fun getGoogleAccessToken(code: String): GoogleTokenResponse
}
