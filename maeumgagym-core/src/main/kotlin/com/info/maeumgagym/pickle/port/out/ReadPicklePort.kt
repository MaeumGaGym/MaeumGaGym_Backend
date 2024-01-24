package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.Pickle

interface ReadPicklePort {

    fun readAll(): List<Pickle>

    fun readById(id: String): Pickle?
}
