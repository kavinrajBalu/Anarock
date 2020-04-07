package com.anarock.cpsourcing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.anarock.cpsourcing.model.*
import com.anarock.cpsourcing.repository.CreateEventRepository
import com.anarock.cpsourcing.repository.LeadRepositry

class CreateEventViewModel(application: Application) : AndroidViewModel(application)
{

     private  var cpSearchString : MutableLiveData<String> = MutableLiveData()
    var cpDetailsList:MutableLiveData<ArrayList<CP>> = MutableLiveData()
     val searchCpResult : LiveData<CPSearchResponse> = Transformations.switchMap(cpSearchString){
         CreateEventRepository.searchCP(getApplication(),it)
     }


    fun getLeadDetails(hint : String):LiveData<ArrayList<LeadSearchData>>
    {
        return LeadRepositry.getLeadSearchResult(hint)
    }
    fun eventCreateAPI(payload: EventCreationPayload):LiveData<Boolean>
    {
       return CreateEventRepository.createEvent(payload,getApplication())
    }

    fun searchCP(hint : String)
    {
      cpSearchString.value = hint
    }

}