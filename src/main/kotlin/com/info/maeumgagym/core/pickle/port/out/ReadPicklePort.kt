package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.Pickle

interface ReadPicklePort {

    fun readById(id: String): Pickle?

    fun readAll(): List<Pickle>

    fun readAllPagedByTagsContaining(simpleTag: String, exactTag: String, idx: Int, size: Int): MutableList<Pickle>
}
