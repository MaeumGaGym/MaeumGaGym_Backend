package com.info.maeumgagym.pickle.port.out

interface GetPreSignedVideoUploadURLPort {

    fun getPreSignedURL(fileType: String): String
}
