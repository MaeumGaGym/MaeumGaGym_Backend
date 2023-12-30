package com.info.maeumgagym.pickle.model

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
)
