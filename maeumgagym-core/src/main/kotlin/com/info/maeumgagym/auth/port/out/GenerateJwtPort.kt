package com.info.maeumgagym.auth.port.out

interface GenerateJwtPort {

    fun generateTokens(subject: String): Pair<String, String>
}
