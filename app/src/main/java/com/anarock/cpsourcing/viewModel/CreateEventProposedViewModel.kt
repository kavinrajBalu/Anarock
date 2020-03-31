package com.anarock.cpsourcing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anarock.cpsourcing.model.EventCreationPayload
import com.anarock.cpsourcing.model.EventCreationResponse
import com.anarock.cpsourcing.model.LeadSearchData
import com.anarock.cpsourcing.repository.CreateEventRepository
import com.anarock.cpsourcing.repository.LeadRepositry

class CreateEventProposedViewModel(application: Application) : AndroidViewModel(application)
{

    fun getLeadDetails(hint : String):LiveData<ArrayList<LeadSearchData>>
    {
        return LeadRepositry.getLeadSearchResult(hint)
    }
    fun eventCreateAPI(payload: EventCreationPayload):LiveData<Boolean>
    {
       return CreateEventRepository.createEvent(payload,getApplication())
    }
}