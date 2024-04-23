package com.info.maeumgagym.core.auth.port.out

interface KakaoGenerateTokenUseCase {

    fun generate(code: String): String
}
