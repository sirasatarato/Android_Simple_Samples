package com.silso.mvvmbasic.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "silesia")
data class Entity(@PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = "id") var msgId: Int = 0,
                  @ColumnInfo(name = "txt") var str: String = "")