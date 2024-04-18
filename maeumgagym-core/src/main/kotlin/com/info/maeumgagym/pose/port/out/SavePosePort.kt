package com.info.maeumgagym.pose.port.out

import com.info.maeumgagym.pose.model.Pose

interface SavePosePort {

    fun save(pose: Pose): Pose
}
