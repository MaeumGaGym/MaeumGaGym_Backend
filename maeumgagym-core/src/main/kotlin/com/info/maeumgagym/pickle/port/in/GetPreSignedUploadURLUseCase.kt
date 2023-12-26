package com.info.maeumgagym.pickle.port.`in`

interface GetPreSignedUploadURLUseCase {

    fun getPreSignedUploadURL(fileType: String): String
}
