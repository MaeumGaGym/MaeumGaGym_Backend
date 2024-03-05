package com.info.maeumgagym.daily.port.out

interface GetPreSignedImageUploadURL {

    fun getPreSignedUrl(folderName: String, objectName: String): String
}
