package com.anarock.cpsourcing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anarock.cpsourcing.model.LeadSearchData
import com.anarock.cpsourcing.repository.LeadRepositry

class CreateEventProposedViewModel(application: Application) : AndroidViewModel(application)
{

    fun getLeadDetails(hint : String):LiveData<ArrayList<LeadSearchData>>
    {
        return LeadRepositry.getLeadSearchResult(hint)
    }
}