package com.info.maeumgagym.pickle.model

import com.info.maeumgagym.user.model.User
import java.time.LocalDateTime

data class Pickle(

    val title: String,

    val description: String?,

    val uploader: User,

    val videoUrl: String,

    val tags: MutableSet<String>,

    val createdAt: LocalDateTime,

    val likes: Int = 0,

    val isDeleted: Boolean = false,

    val id: Long? = null
)
