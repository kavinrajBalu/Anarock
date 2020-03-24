package com.anarock.cpsourcing.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anarock.cpsourcing.model.CustomAppBar

class SharedUtilityViewModel : ViewModel()
{
    private var bottomNavigationVisibility : MutableLiveData<Boolean> = MutableLiveData()

    private var toolBarVisibility : MutableLiveData<Boolean> = MutableLiveData()

    private var customAppBar : MutableLiveData<CustomAppBar> = MutableLiveData()

    private var customStatusBar : MutableLiveData<Int> = MutableLiveData()

    fun setCustomStatusBar(color : Int)
    {
        customStatusBar.value = color
    }

    fun getCustomStatusBar():LiveData<Int>
    {
        return customStatusBar
    }

    fun setCustomToolBar(customAppBar: CustomAppBar)
    {
        this.customAppBar.value = customAppBar
    }

    fun getCustomToolBar():LiveData<CustomAppBar>
    {
        return customAppBar
    }

    fun setToolBarVisibility(state : Boolean)
    {
        toolBarVisibility.value = state
    }

    fun getToolBarVisibility():LiveData<Boolean>
    {
        return toolBarVisibility
    }
    fun  setBottomNavigationVisibility(state : Boolean)
    {
        bottomNavigationVisibility.value = state
    }

    fun getBottomNavigationVisibility(): LiveData<Boolean>
    {
        return  bottomNavigationVisibility
    }

}