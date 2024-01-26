package com.info.maeumgagym.pickle.model

import com.info.maeumgagym.user.model.User

data class PickleLike(

    val pickle: Pickle,

    val user: User,

    val isNew: Boolean = true
)
