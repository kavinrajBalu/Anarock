package com.anarock.cpsourcing.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anarock.cpsourcing.entities.CallLogs

@Dao
interface CallLogsDao {

    @Insert
    fun insertAll(callLogs: CallLogs)

    @Query("SELECT * FROM call_logs")
    fun getAll():LiveData<List<CallLogs>>

    @Query("DELETE FROM call_logs")
    fun deleteAll()
}