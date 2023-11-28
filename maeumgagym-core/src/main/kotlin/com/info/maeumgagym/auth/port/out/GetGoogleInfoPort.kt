package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.google.dto.response.GoogleInfoResponse

interface GetGoogleInfoPort {

    fun getGoogleInfo(accessToken: String): GoogleInfoResponse
}
