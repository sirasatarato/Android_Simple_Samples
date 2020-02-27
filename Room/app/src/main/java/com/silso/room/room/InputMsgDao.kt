package com.silso.room.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InputMsgDao {
    @Query("SELECT * FROM inputMsg")
    fun getAll(): LiveData<List<InputMsg>>

    @Insert
    fun insertMsg(inputMsg: InputMsg)
}