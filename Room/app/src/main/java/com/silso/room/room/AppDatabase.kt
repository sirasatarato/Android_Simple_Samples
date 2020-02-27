package com.silso.room.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InputMsg::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun inputMsgDao(): InputMsgDao

    companion object {
        val DB_NAME = "livedata-db"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {}).build()
        }
    }
}