package com.info.maeumgagym.core.auth.port.out

import io.jsonwebtoken.Claims

interface ParseAppleTokenPort {

    fun parseIdToken(token: String): Claims
}
