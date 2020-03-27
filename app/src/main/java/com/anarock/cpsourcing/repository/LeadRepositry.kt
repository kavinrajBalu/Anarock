package com.anarock.cpsourcing.repository

import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.model.LeadSearchData

class LeadRepositry {
    companion object
    {
        fun getLeadSearchResult(hint:String):MutableLiveData<ArrayList<LeadSearchData>>
        {
            var searchResult: ArrayList<LeadSearchData>? = ArrayList()
            searchResult?.add(LeadSearchData("kavin","9899118990809"))
            searchResult?.add(LeadSearchData("balu","989911"))
            searchResult?.add(LeadSearchData("sample1","9899118990809"))
            searchResult?.add(LeadSearchData("sample2","9899118990809"))
            var searchMutableLiveData :MutableLiveData<ArrayList<LeadSearchData>>  = MutableLiveData()
            searchMutableLiveData.value = searchResult
            return searchMutableLiveData
        }
    }
}