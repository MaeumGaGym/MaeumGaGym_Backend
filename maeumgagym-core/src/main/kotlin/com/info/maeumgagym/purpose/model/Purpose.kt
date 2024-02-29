package com.info.maeumgagym.purpose.model

import com.info.maeumgagym.purpose.dto.response.PurposeInfoResponse
import com.info.maeumgagym.user.model.User
import java.time.LocalDate

data class Purpose(
    val title: String,
    val content: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val user: User,
    val id: Long? = null
) {
    fun toResponse(): PurposeInfoResponse = PurposeInfoResponse(
        title = title,
        content = content,
        startDate = startDate,
        endDate = endDate
    )
}
