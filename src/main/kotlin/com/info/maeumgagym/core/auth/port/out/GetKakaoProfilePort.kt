package com.info.maeumgagym.core.auth.port.out

import com.info.maeumgagym.core.auth.dto.response.KakaoProfileResponse

interface GetKakaoProfilePort {

    fun getProfile(accessToken: String): KakaoProfileResponse
}
