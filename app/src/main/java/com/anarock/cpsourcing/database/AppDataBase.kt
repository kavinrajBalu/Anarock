package com.anarock.cpsourcing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anarock.cpsourcing.dao.CallLogsDao
import com.anarock.cpsourcing.entities.CallLogs

@Database(entities = [CallLogs::class],version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun callLogsDao():CallLogsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context)= INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                AppDataBase::class.java, "cp-sourcing.db")
            .build()
    }

}