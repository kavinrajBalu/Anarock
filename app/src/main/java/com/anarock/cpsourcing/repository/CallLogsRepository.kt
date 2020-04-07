package com.anarock.cpsourcing.repository

import androidx.lifecycle.LiveData
import com.anarock.cpsourcing.dao.CallLogsDao
import com.anarock.cpsourcing.entities.CallLogs

class CallLogsRepository(private val callLogsDao: CallLogsDao)
{
    val allCallLogs: LiveData<List<CallLogs>> = callLogsDao.getAll()

     fun insert(callLogs: CallLogs)
    {
        callLogsDao.insertAll(callLogs)
    }

    fun deleteAll()
    {
        callLogsDao.deleteAll()
    }
}