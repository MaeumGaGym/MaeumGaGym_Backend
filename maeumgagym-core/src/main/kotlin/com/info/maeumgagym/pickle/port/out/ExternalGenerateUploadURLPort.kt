package com.info.maeumgagym.pickle.port.out

interface ExternalGenerateUploadURLPort {

    fun generateURL(videoId: String): String

    fun getPreSignedURL(fileType: String): String
}
