package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.Pickle

interface DeletePicklePort {

    fun delete(pickle: Pickle)
}
