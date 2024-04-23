package com.info.maeumgagym.core.auth.port.out

interface RevokeKakaoTokenPort {

    fun revoke(accessToken: String)
}
