package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.DeletedAt

interface SaveDeletedAtPort {

    fun save(domain: DeletedAt): DeletedAt
}
