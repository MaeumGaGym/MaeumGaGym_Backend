package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.Pickle

interface SavePicklePort {

    fun save(pickle: Pickle): Pickle
}
