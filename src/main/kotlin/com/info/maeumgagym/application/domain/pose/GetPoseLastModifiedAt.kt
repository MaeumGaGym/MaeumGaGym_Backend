package com.info.maeumgagym.application.domain.pose

import java.time.LocalDateTime

interface GetPoseLastModifiedAt {

    operator fun invoke(): LocalDateTime
}
