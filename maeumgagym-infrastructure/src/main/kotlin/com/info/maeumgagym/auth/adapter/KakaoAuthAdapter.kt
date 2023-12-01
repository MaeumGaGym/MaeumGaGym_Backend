package com.info.maeumgagym.auth.adapter

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.dto.response.KakaoInfoResponse
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.feign.oauth.kakao.KakaoInfoClient

@WebAdapter
class KakaoAuthAdapter(
    private val kakaoInfoClient: KakaoInfoClient
) : GetKakaoInfoPort {
    override fun getInfo(accessToken: String): KakaoInfoResponse = kakaoInfoClient.kakaoInfo("Bearer $accessToken")
}
