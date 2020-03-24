package com.anarock.cpsourcing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.model.CountryResponseModel
import com.anarock.cpsourcing.model.LoginResponseModel
import com.anarock.cpsourcing.model.OtpResponseModel
import com.anarock.cpsourcing.model.TenantDomainResponseModel
import com.anarock.cpsourcing.repository.LoginRepository
import com.google.gson.Gson
import okhttp3.RequestBody

class LoginSharedViewModel(application: Application) : AndroidViewModel(application)
 {
     private var loginState : MutableLiveData<LoginState>  = MutableLiveData()
     private var mLoginRepository: LoginRepository? = null

     enum class LoginState
     {
        LOGIN_SUCCESS,
         LOGIN_FAILED
     }

     init {

         loginState.value = LoginState.LOGIN_SUCCESS
     }

     fun fetchTenantDomain(tenantName : String) : LiveData<TenantDomainResponseModel>{
           return LoginRepository.fetchTenantConfig(getApplication(), tenantName)
     }

     fun fetchCountryCodes() : LiveData<CountryResponseModel>{
         return LoginRepository.fetchCountryCodes(getApplication())
     }

     fun loginTriggerOTP(countryId: Int, phoneNo : String) : LiveData<LoginResponseModel>{
         return LoginRepository.triggerOTP(getApplication(), countryId, phoneNo)
     }

     fun verifyOTP(otp: String, countryId: Int, phoneNo : String) : LiveData<OtpResponseModel>{
         return LoginRepository.verifyOTP(getApplication(), countryId, phoneNo, otp)
     }

     fun setLoginState(loginstate : LoginState)
     {
         this.loginState.value = loginstate
     }

     fun getLoginState():LiveData<LoginState>
     {
         return loginState
     }

 }