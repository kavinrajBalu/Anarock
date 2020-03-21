package com.anarock.cpsourcing.utilities

import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Patterns
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*
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

        fun getSecondsDifference(startTime : Date, endtime : Date):Long
        {
            val diff: Long = endtime.time - startTime.time
            return  (diff/1000) % 60
        }

        @SuppressLint("MissingPermission")
        fun makeCall(context: Context, phoneNumber : String)
        {
            val uri = "tel:" + phoneNumber.trim()
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}