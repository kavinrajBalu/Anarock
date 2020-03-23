package com.anarock.cpsourcing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.model.*
import com.anarock.cpsourcing.repository.LoginRepository

class LoginSharedViewModel : AndroidViewModel
 {

     public constructor(application: Application) : super(application)

     private var bottomNavigationVisibility : MutableLiveData<Boolean>  = MutableLiveData()
     private var loginState : MutableLiveData<LoginState>  = MutableLiveData()
     private var mLoginRepository: LoginRepository? = null
     private var lightTheme : MutableLiveData<ToolBarTheme>  = MutableLiveData()


     enum class LoginState
     {
        LOGIN_SUCCESS,
         LOGIN_FAILED
     }

     init {

         loginState.value = LoginState.LOGIN_FAILED
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

     fun  setBottomNavigationVisibility(state : Boolean)
     {
        bottomNavigationVisibility.value = state
     }


     fun getBottomNavigationVisibility():LiveData<Boolean>
     {
         return  bottomNavigationVisibility
     }

     fun setLoginState(loginstate : LoginState)
     {
         this.loginState.value = loginstate
     }

     fun getLoginState():LiveData<LoginState>
     {
         return loginState
     }

     fun getToolbarTheme():LiveData<ToolBarTheme>{
         return lightTheme
     }

     fun setToolbarTheme(state: ToolBarTheme){
         lightTheme.value = state
     }

     fun getOTP():LiveData<String>{
         return otpMsg
     }
     companion object{
         private var otpMsg : MutableLiveData<String>  = MutableLiveData()

         fun setOTP(otp:String){
             otpMsg.value = otp
         }
     }

 }