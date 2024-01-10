package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.Pickle

interface ReadPickleByIdPort {

    fun readPickleById(id: String): Pickle?
}
