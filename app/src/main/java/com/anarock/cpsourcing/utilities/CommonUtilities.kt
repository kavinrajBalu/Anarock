package com.anarock.cpsourcing.utilities

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
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.toRegex().matches(email);
        }
        fun isValidPhoneNumber(number: String): Boolean {
            //TODO : Make condition to validate based on the country
            var pattern: Pattern = Pattern.compile(indiaMobilePattern)
            return pattern.matcher(number).find()
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