package com.anarock.cpsourcing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.model.*
import com.anarock.cpsourcing.repository.CreateEventRepository
import com.anarock.cpsourcing.repository.LeadRepositry

class CreateEventViewModel(application: Application) : AndroidViewModel(application)
{

     var cpNamesList : MutableLiveData<ArrayList<String>> = MutableLiveData()

    fun getLeadDetails(hint : String):LiveData<ArrayList<LeadSearchData>>
    {
        return LeadRepositry.getLeadSearchResult(hint)
    }
    fun eventCreateAPI(payload: EventCreationPayload):LiveData<Boolean>
    {
       return CreateEventRepository.createEvent(payload,getApplication())
    }

    fun searchCP(payload : CPSearchPayload) : LiveData<HashMap<Int,String>>
    {
        val response = CreateEventRepository.searchCP(getApplication(),payload)
       return getCPNamesList(response)
    }

    private fun getCPNamesList(response: MutableLiveData<CPSearchResponse>): LiveData<HashMap<Int,String>> {
          var cpMap : MutableLiveData<HashMap<Int,String>> = MutableLiveData()
           response.value?.response?.let {
               for(cp in it)
               {
                   cp.id?.let { it1 -> cp.name?.let { it2 -> cpMap.value?.put(it1,it2) } }
               }
           }
        return cpMap
    }
}