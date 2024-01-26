package com.info.maeumgagym.pickle.model

import com.info.maeumgagym.user.model.User

data class PickleLike(

    val pickleId: String,

    val user: User,

    val isNew: Boolean = true
)
