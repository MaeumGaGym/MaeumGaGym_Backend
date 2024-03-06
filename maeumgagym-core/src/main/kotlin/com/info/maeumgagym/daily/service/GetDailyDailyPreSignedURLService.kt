package com.info.maeumgagym.daily.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.daily.dto.response.PreSignedURLResponse
import com.info.maeumgagym.daily.port.`in`.GetDailyPreSignedURLUseCase
import com.info.maeumgagym.daily.port.out.GetPreSignedImageUploadURL
import java.time.LocalDate

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
