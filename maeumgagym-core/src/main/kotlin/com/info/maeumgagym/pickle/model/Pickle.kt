package com.info.maeumgagym.pickle.model

import com.info.maeumgagym.pickle.dto.response.PickleResponse
import com.info.maeumgagym.user.dto.response.UserResponse
import com.info.maeumgagym.user.model.User
import java.time.LocalDateTime

data class Pickle(

    val videoId: String,

    val title: String,

    val description: String?,

    val uploader: User,

    val likeCount: Long = 0,

    val commentCount: Long = 0,

    val tags: MutableSet<String> = mutableSetOf(),

    val createdAt: LocalDateTime? = null,

    val isDeleted: Boolean = false
) {
    companion object {
        fun Pickle.toResponse(videoURL: String): PickleResponse =
            PickleResponse(
                videoId = videoId,
                videoURL = videoURL,
                title = title,
                description = description,
                tags = tags.toList(),
                likeCount = likeCount,
                commentCount = commentCount,
                userInfo = UserResponse(
                    nickname = uploader.nickname,
                    profileImage = uploader.profileImage
                )
            )
    }
}
