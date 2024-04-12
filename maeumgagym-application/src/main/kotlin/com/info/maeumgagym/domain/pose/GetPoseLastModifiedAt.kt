package com.info.maeumgagym.domain.pose

import java.time.LocalDateTime

interface GetPoseLastModifiedAt {

    operator fun invoke(): LocalDateTime
}
