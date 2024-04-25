package com.info.maeumgagym.core.pose.port.out

import com.info.maeumgagym.core.pose.model.Pose

interface SavePosePort {

    fun save(pose: Pose): Pose
}
