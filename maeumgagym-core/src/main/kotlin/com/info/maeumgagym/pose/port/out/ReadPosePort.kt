package com.info.maeumgagym.pose.port.out

import com.info.maeumgagym.pose.model.Pose

interface ReadPosePort {

    fun readById(id: Long): Pose?
}
