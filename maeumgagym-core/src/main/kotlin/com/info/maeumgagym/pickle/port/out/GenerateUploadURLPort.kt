package com.info.maeumgagym.pickle.port.out

interface GenerateUploadURLPort {

    fun generateURL(videoId: String): String
}
