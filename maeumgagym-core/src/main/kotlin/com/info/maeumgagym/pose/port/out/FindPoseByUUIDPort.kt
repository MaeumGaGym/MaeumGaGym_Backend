package com.info.maeumgagym.pose.port.out

import com.info.maeumgagym.pose.model.Pose
import java.util.*

interface FindPoseByUUIDPort {

    fun findById(id: UUID): Pose?
}
