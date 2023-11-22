package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.dto.response.KakaoTokenResponse
import com.info.maeumgagym.auth.port.`in`.GetKakaoAccessTokenUseCase
import com.info.maeumgagym.auth.port.out.GetKakaoAccessTokenPort
import org.springframework.stereotype.Service

@Service
class GetKakaoAccessTokenService(
    private val getKakaoAccessTokenPort: GetKakaoAccessTokenPort
): GetKakaoAccessTokenUseCase {
    override fun getAccessToken(code: String): KakaoTokenResponse {
        return getKakaoAccessTokenPort.getAccessToken(code)
    }
}
