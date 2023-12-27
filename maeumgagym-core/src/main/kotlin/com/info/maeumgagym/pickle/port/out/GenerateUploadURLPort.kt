package com.info.maeumgagym.pickle.port.out

interface GenerateUploadURLPort {

    fun generateUploadURL(videoId: Long): String
}
