package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.Pickle

interface ReadAllPicklesPort {

    fun readAllPickles(): List<Pickle>
}
