package com.anarock.cpsourcing.utilities

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.util.regex.Pattern

class CommonUtilities{
    companion object {
        private const val indiaMobilePattern:String = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"
        private const val mobilePattern:String = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?\$"

        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.toRegex().matches(email);
        }
        fun isValidPhoneNumber(number: String): Boolean {
            //TODO : Make condition to validate based on the country
            var pattern: Pattern = Pattern.compile(mobilePattern)
            return pattern.matcher(number).find()
        }

        fun isNetworkConnected(mContext: Context) : Boolean{
            return if (NetworkUtil.isInternetConnected(mContext)){
                true
            } else {
                Log.e("NetworkConnected", "Check your internet connection!");
                Toast.makeText(mContext, "Check your internet connection!", Toast.LENGTH_LONG)
                false
            }
        }

        fun notnull(data: String?): Boolean {
            return !(data == null || data.trim { it <= ' ' }
                .isEmpty() || data == "null" || data == "")
        }

       /* fun hideKeyboard(mContext: Context) {
            // Check if no view has focus:
            try {
                val view: View = mContext.getCurrentFocus()
                if (view != null) {
                    val inputManager =
                        mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(
                        view.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
    }


}