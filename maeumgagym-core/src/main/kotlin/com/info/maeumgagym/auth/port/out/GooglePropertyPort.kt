package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.google.dto.response.GooglePropertyResponse

interface GooglePropertyPort {

    fun getProperty(): GooglePropertyResponse
}
