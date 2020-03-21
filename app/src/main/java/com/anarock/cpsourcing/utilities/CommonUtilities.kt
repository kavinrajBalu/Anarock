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
import androidx.core.content.ContextCompat.startActivity
import com.anarock.cpsourcing.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class CommonUtilities {
    companion object {
        private const val indiaMobilePattern: String =
            "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"
        private const val mobilePattern: String =
            "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?\$"

        private const val recipient = "ravina.jain@anarock.com"
        private const val subject = "Unable to login"

        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.toRegex().matches(email);
        }

        fun isValidPhoneNumber(number: String): Boolean {
            //TODO : Make condition to validate based on the country
            var pattern: Pattern = Pattern.compile(mobilePattern)
            return pattern.matcher(number).find()
        }

        fun isNetworkConnected(mContext: Context): Boolean {
            return if (NetworkUtil.isInternetConnected(mContext)) {
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

        fun getSecondsDifference(startTime: Date, endtime: Date): Long {
            val diff: Long = endtime.time - startTime.time
            return (diff / 1000) % 60
        }

        @SuppressLint("MissingPermission")
        fun makeCall(context: Context, phoneNumber: String) {
            val uri = "tel:" + phoneNumber.trim()
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun getEmailSupport(mContext: Context) {
            val message = mContext.getString(R.string.email_support_template)
            /*ACTION_SEND action to launch an email client installed on your Android device.*/
            val mIntent = Intent(Intent.ACTION_SEND)
            /*To send an email you need to specify mailto: as URI using setData() method
            and data type will be to text/plain using setType() method*/
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            // put recipient email in intent
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            //put the Subject in the intent
            mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            //put the message in the intent
            mIntent.putExtra(Intent.EXTRA_TEXT, message)


            try {
                //start email intent
                startActivity(mContext, Intent.createChooser(mIntent, "Choose Email Client..."), null)
            } catch (e: Exception) {
                //if any thing goes wrong for example no email client application or any exception
                //get and show exception message
                Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}