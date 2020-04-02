package com.anarock.cpsourcing.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anarock.cpsourcing.model.CpFormData
import com.anarock.cpsourcing.utilities.CreateCpDialogUtil

class ChannelPartnerViewModel(application: Application) : AndroidViewModel(application) {

    fun editData(view: View): LiveData<CpFormData> {
      return  CreateCpDialogUtil.openCreateDialog(view, getApplication())
    }
}