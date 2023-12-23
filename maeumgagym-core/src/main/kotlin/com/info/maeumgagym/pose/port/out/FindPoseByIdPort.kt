package com.info.maeumgagym.pose.port.out

import com.info.maeumgagym.pose.model.Pose

interface FindPoseByIdPort {

    fun findById(id: Long): Pose?
}
