package com.info.maeumgagym.daily.port.out

interface GetPreSignedImageUploadURL {

    fun getPreSignedUrl(oauthId: String, objectName: String): String
}
