package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.KakaoProfileResponse

interface GetKakaoProfilePort {

    fun getProfile(accessToken: String): KakaoProfileResponse
}
