package com.info.maeumgagym.core.daily.port.out

interface GetPreSignedImageUploadURL {

    fun getPreSignedUrl(oauthId: String, objectName: String): String
}
