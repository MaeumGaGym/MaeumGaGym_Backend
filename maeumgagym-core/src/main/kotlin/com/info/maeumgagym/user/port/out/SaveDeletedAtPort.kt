package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.DeleteAt

interface SaveDeletedAtPort {

    fun save(domain: DeleteAt): DeleteAt
}
