package com.info.maeumgagym.controller.pickle.dto.response

import com.info.maeumgagym.controller.user.dto.UserWebResponse
import com.info.maeumgagym.pickle.dto.response.PickleResponse

data class PickleWebResponse(
    val id: Long,
    val videoUrl: String,
    val title: String,
    val description: String?,
    val tags: List<String>,
    val likeCount: Long,
    val commentCount: Long,
    val userInfo: UserWebResponse
) {

    companion object {
        fun toWebResponse(response: PickleResponse): PickleWebResponse =
            response.run {
                PickleWebResponse(
                    id = id,
                    videoUrl = videoUrl,
                    title = title,
                    description = description,
                    tags = tags,
                    likeCount = likeCount,
                    commentCount = commentCount,
                    userInfo = UserWebResponse.toWebResponse(userInfo)
                )
            }
    }
}
