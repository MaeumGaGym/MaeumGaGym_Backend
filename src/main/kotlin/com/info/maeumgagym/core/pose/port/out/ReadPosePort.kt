package com.info.maeumgagym.core.pose.port.out

import com.info.maeumgagym.core.pose.model.Pose
import java.time.LocalDateTime

interface ReadPosePort {

    fun readAll(): List<Pose>

    fun getLastModifiedAt(): LocalDateTime

    fun readById(id: Long): Pose?

    fun readByTag(tag: String): List<Pose>

    fun readAllRandomLimit30(): List<Pose>
}
