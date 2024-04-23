package com.info.maeumgagym.core.daily.service

import com.info.maeumgagym.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.daily.dto.response.PreSignedURLResponse
import com.info.maeumgagym.core.daily.port.`in`.GetDailyPreSignedURLUseCase
import com.info.maeumgagym.core.daily.port.out.GetPreSignedImageUploadURL

@ReadOnlyUseCase
internal class GetDailyDailyPreSignedURLService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val getPreSignedImageUploadURL: GetPreSignedImageUploadURL
) : GetDailyPreSignedURLUseCase {

    override fun getUploadUrl(title: String): PreSignedURLResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val url = getPreSignedImageUploadURL.getPreSignedUrl(user.oauthId, title)

        return PreSignedURLResponse(url)
    }
}
