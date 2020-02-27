package com.silso.mvvmbasic.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "my_db.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}