package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.Pickle

interface SavePicklePort {

    fun savePickle(pickle: Pickle): Pickle
}
