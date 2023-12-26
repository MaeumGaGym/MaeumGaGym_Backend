package com.info.maeumgagym.pickle.port.out

interface GetPreSignedURLPort {

    fun getPreSignedUploadURL(fileType: String): String
}
