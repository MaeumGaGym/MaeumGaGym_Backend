package com.info.maeumgagym.auth.port.out

interface RevokeKakaoTokenPort {

    fun revoke(accessToken: String)
}
