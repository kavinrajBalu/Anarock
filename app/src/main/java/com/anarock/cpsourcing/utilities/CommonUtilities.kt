package com.anarock.cpsourcing.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.anarock.callrecord.CallRecord
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.callLogs.ConnectAppCallLogsService
import java.io.File
import java.io.FileInputStream
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

        /*fun isValidPhoneNumber(number: String): Boolean {
            //TODO : Make condition to validate based on the country
            var pattern: Pattern = Pattern.compile(mobilePattern)
            return pattern.matcher(number).find()
        }*/

        fun isValidPhoneNumber(num: String): Boolean {
            return num.trim().length in 7..10
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

        fun playCall(path:String) : MediaPlayer
        {
            val mp = MediaPlayer()
            try {
                val filePath = File(path)
                val fileInputStream = FileInputStream(filePath)
                mp.setDataSource(fileInputStream.fd)
                mp.prepare()
                mp.start()
            }
            catch (e: Exception)
            {
                e.printStackTrace()
            }

            return  mp
        }

        fun getCallRecordingFilePath(callRecord: CallRecord):String
        {
            return callRecord.recordDirPath+"/"+callRecord.recordDirName+"/"+callRecord.recordFileName
        }

        fun hideKeyboard(activity: Activity) {
            val imm =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


        fun showKeyBoard(context: Context)
        {
            val inputMethodManager =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                0
            )
        }

           fun getEmailSupport(mContext: Context) {
            val message = mContext.getString(R.string.email_support_template)
            /*ACTION_SEND action to launch an email client installed on your Android device.*/
            val mIntent = Intent(Intent.ACTION_SEND)
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
//            mIntent.addCategory(Intent.CATEGORY_APP_EMAIL);
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            mIntent.putExtra(Intent.EXTRA_TEXT, message)


            try {
                //start email intent
                startActivity(
                    mContext,
                    Intent.createChooser(mIntent, "Choose Email Client..."),
                    null
                )
            } catch (e: Exception) {
                //if any thing goes wrong for example no email client application or any exception
                //get and show exception message
                Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
            }
        }

        fun isPackageInstalled(
            packageName: String,
            context: Context
        ): Boolean {
            return try {
                val packageManager = context!!.packageManager
                packageManager.getPackageInfo(packageName, 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }

        }

        fun scheduleFetchConnectAppCallLogs(context: Context)
        {
            val componentName = ComponentName(context,ConnectAppCallLogsService::class.java)
            val jobInfo = JobInfo.Builder(0,componentName)
            jobInfo.setMinimumLatency(1*1000)
            jobInfo.setOverrideDeadline(1*3000)
            val jobScheduler = context.getSystemService(JobScheduler::class.java)
            jobScheduler.schedule(jobInfo.build())
        }

    }
}