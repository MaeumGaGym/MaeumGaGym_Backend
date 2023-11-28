package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.KakaoInfoResponse

interface GetKakaoInfoPort {
    fun getInfo(accessToken: String): KakaoInfoResponse
}
