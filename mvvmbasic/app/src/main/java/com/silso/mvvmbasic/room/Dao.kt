package com.silso.mvvmbasic.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao{
    @Insert
    fun insert(entity: Entity)

    @Query("SELECT * FROM silesia")
    fun getAll(): LiveData<List<Entity>>
}