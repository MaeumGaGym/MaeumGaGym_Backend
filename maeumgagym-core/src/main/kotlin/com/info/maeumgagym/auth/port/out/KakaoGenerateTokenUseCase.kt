package com.info.maeumgagym.auth.port.out

interface KakaoGenerateTokenUseCase {

    fun generate(code: String): String
}
