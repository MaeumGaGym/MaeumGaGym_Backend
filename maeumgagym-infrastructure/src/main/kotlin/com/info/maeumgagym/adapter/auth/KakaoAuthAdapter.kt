package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.dto.response.KakaoInfoResponse
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.feign.oauth.kakao.KakaoInfoClient
import org.springframework.stereotype.Component

@Component
class KakaoAuthAdapter(
    private val kakaoInfoClient: KakaoInfoClient
) : GetKakaoInfoPort {
    override fun getInfo(accessToken: String): KakaoInfoResponse = kakaoInfoClient.kakaoInfo("Bearer $accessToken")
}
