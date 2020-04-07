package com.anarock.cpsourcing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.anarock.cpsourcing.database.AppDataBase
import com.anarock.cpsourcing.entities.CallLogs
import com.anarock.cpsourcing.repository.CallLogsRepository
import com.anarock.cpsourcing.utilities.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CallLogsViewModel(application: Application) : AndroidViewModel(application) {
    private val callLogsRepository: CallLogsRepository
    val allCallLogs : LiveData<List<CallLogs>>

    init {
        val callLogsDao = AppDataBase(application).callLogsDao()
        callLogsRepository = CallLogsRepository(callLogsDao)
        allCallLogs = callLogsRepository.allCallLogs
    }

    fun insert(callLogs: CallLogs) = viewModelScope.launch(Dispatchers.IO) {
        callLogsRepository.insert(callLogs)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        callLogsRepository.deleteAll()
    }
}