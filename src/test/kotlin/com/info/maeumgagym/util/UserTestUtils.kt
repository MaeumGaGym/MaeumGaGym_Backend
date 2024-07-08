package com.info.maeumgagym.util

import com.info.maeumgagym.core.user.model.GenderModel
import com.info.maeumgagym.core.user.model.PhysicalInfoModel
import com.info.maeumgagym.core.user.model.Role
import com.info.maeumgagym.core.user.model.User

object UserTestUtils {

    val TEST_USER
        get() = User(
            nickname = "testuser",
            roles = mutableListOf(Role.USER),
            oauthId = "123456790",
            physicalInfoModel = PhysicalInfoModel(
                weight = 60.0,
                height = 180.0,
                genderModel = GenderModel.MAN
            )
        )
}
