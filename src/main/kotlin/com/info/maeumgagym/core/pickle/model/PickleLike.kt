package com.info.maeumgagym.core.pickle.model

import com.info.maeumgagym.core.user.model.User

data class PickleLike(

    val pickle: Pickle,

    val user: User,

    val id: Long? = null
)
