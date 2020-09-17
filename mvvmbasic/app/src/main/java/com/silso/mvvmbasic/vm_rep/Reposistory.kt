package com.silso.mvvmbasic.vm_rep

import com.silso.mvvmbasic.room.Dao
import com.silso.mvvmbasic.room.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Reposistory(private val inputMsgDao: Dao?) {
    fun getAllMsgs() = inputMsgDao?.getAll()

    suspend fun insert(inputMsg: Entity) {
        withContext(Dispatchers.IO) {
            inputMsgDao?.insert(inputMsg)
        }
    }

    companion object {
        @Volatile
        private var instance: Reposistory? = null

        fun getInstance(inputMsgDao: Dao?) =
            instance ?: synchronized(this) {
                instance
                    ?: Reposistory(inputMsgDao).also { instance = it }
            }

    }
}