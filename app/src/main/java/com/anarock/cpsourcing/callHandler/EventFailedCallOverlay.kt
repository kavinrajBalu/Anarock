package com.anarock.cpsourcing.callHandler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.interfaces.PhoneCallStatusCallBack
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.utilities.DateTimeUtils.getCustomDateFormat
import com.anarock.cpsourcing.utilities.DateTimeUtils.getTimeEightHourAhead
import com.anarock.cpsourcing.utilities.DateTimeUtils.getTimeFiveHourAhead
import com.anarock.cpsourcing.utilities.DateTimeUtils.getTimeOneHourAhead
import com.anarock.cpsourcing.utilities.DateTimeUtils.getTimeTwentyMinutesAhead
import com.anarock.cpsourcing.utilities.DateTimeUtils.isItAfterWorkingTime
import com.anarock.cpsourcing.utilities.DateTimeUtils.isItElevenAM
import com.anarock.cpsourcing.utilities.DateTimeUtils.isItWorkingTime
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*


class EventFailedCallOverlay : Service() {

    private lateinit var failedCallWindowManager: WindowManager
    private lateinit var failedCallOverlayView: View
    private lateinit var reScheduleCallWindowManager: WindowManager
    private lateinit var reScheduleCallOverlayView: View
    lateinit var callStateListener : CallStateListener
    lateinit var  params: WindowManager.LayoutParams
    lateinit var currentTime : Calendar
    // schedule screen
    private lateinit var alternateTimeOne  : Chip
    private lateinit var alternateTimeTwo  : Chip
    private lateinit var alternateTimeThree  : Chip
    private lateinit var alternateTimeFour  : Chip
    private lateinit var alternateTimeFive  : Chip
    private lateinit var alternateTimeSix  : Chip
    private lateinit var timePickerGroup : ChipGroup
    private lateinit var confirmRescheduleTime  : TextView
    private lateinit var reScheduleBack : ImageView
    private lateinit var autoScheduleTime : TextView
    private val DATE_FORMAT = "hh:mm a"

    //call failed screen
    private lateinit var close : ImageView
    private lateinit var confirmSchedule : TextView
    private lateinit var rescheduleTime : TextView
    private lateinit var callAgain : Button


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        failedCallOverlayView = View.inflate(ContextThemeWrapper(this, R.style.AppTheme), R.layout.event_failed_overlay,null)
        reScheduleCallOverlayView = View.inflate(ContextThemeWrapper(this, R.style.AppTheme), R.layout.reschedule_call_view,null)
        getAllViewReferences()
        initView()
            params  = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER //Initially view will be added to top-left corner

        //Add the view to the window
        failedCallWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        reScheduleCallWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        failedCallWindowManager.addView(failedCallOverlayView, params)

            close.setOnClickListener(View.OnClickListener {
                stopSelf()
            })

        confirmSchedule.findViewById<TextView>(R.id.done)
            .setOnClickListener(View.OnClickListener {
                stopSelf()
            })

       callAgain.setOnClickListener {

            failedCallWindowManager.removeView(failedCallOverlayView)

           val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

             callStateListener   = CallStateListener(object : PhoneCallStatusCallBack {
                override fun onCallSuccess() {
                    telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
                }

                override fun onCallFailed() {
                    telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
                    failedCallWindowManager.addView(failedCallOverlayView, params)
                    initView()
                }
            })
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)

            CommonUtilities.makeCall(applicationContext,"8903653203")
        }

        rescheduleTime.setOnClickListener {
            showRescheduleScreen()
        }

        reScheduleBack.setOnClickListener {
            showCallFailedScreen()
        }

        confirmRescheduleTime.setOnClickListener {
            showCallFailedScreen()
        }

        timePickerGroup.setOnCheckedChangeListener { chipGroup, id ->
            val chip = chipGroup.findViewById<Chip>(id)

        }



    }

    private fun initView() {
        currentTime = Calendar.getInstance()
        if(isItWorkingTime(getTimeTwentyMinutesAhead(currentTime)))
        {
            autoScheduleTime.text= "Auto-scheduled call at ${getCustomDateFormat(DATE_FORMAT, getTimeTwentyMinutesAhead(currentTime))}"
        }
        else
        {

        }
    }

    private fun getAllViewReferences() {

        //Failed call screen views
        close = failedCallOverlayView.findViewById(R.id.close)
        confirmSchedule = failedCallOverlayView.findViewById(R.id.done)
        callAgain = failedCallOverlayView.findViewById(R.id.call_again)
        rescheduleTime = failedCallOverlayView.findViewById(R.id.re_schedule)
        autoScheduleTime  = failedCallOverlayView.findViewById(R.id.auto_schedule_message)
        // Reschedule screen views
        alternateTimeOne = reScheduleCallOverlayView.findViewById(R.id.alternate_time_one)
        alternateTimeTwo = reScheduleCallOverlayView.findViewById(R.id.alternate_time_two)
        alternateTimeThree = reScheduleCallOverlayView.findViewById(R.id.alternate_time_three)
        alternateTimeFour = reScheduleCallOverlayView.findViewById(R.id.alternate_time_four)
        alternateTimeFive = reScheduleCallOverlayView.findViewById(R.id.alternate_time_five)
        alternateTimeSix = reScheduleCallOverlayView.findViewById(R.id.alternate_time_six)
        timePickerGroup = reScheduleCallOverlayView.findViewById(R.id.chipGroup)
        reScheduleBack = reScheduleCallOverlayView.findViewById(R.id.back)
        confirmRescheduleTime = reScheduleCallOverlayView.findViewById(R.id.conform_schedule)
    }

    private fun showCallFailedScreen() {
        failedCallWindowManager.removeView(reScheduleCallOverlayView)
        reScheduleCallWindowManager.addView(failedCallOverlayView, params)
    }

    private fun showRescheduleScreen() {
        failedCallWindowManager.removeView(failedCallOverlayView)
        reScheduleCallWindowManager.addView(reScheduleCallOverlayView, params)
        showViewBasedOnWorkingTime()
        alternateTimeFour.text = "At ${getCustomDateFormat(DATE_FORMAT,getTimeEightHourAhead(currentTime))}"
    }

    private fun showViewBasedOnWorkingTime() {

        if(isItAfterWorkingTime(getTimeTwentyMinutesAhead(currentTime)))
        {
            alternateTimeOne.visibility = View.GONE
        }
        else
        {
            alternateTimeOne.visibility = View.VISIBLE
        }
        if(isItAfterWorkingTime(getTimeOneHourAhead(currentTime)))
        {
            alternateTimeTwo.visibility = View.GONE
        }
        else
        {
            alternateTimeTwo.visibility = View.VISIBLE
        }
        if(isItWorkingTime(getTimeFiveHourAhead(currentTime)))
        {
            showScheduleTime(alternateTimeThree,getTimeFiveHourAhead(currentTime))
        }
        else
        {
            alternateTimeThree.visibility = View.GONE
        }

        if(isItWorkingTime(getTimeEightHourAhead(currentTime)))
        {

            showScheduleTime(alternateTimeFour, getTimeEightHourAhead(currentTime))
        }
        else
        {
            alternateTimeFour.visibility = View.GONE
        }

    }

    private fun showScheduleTime(
        view: Chip,
        calendar: Calendar
    ) {

        if(isItElevenAM(calendar))
        {
            view.visibility = View.GONE
        }
        else
        {
            view.text = "At ${getCustomDateFormat(DATE_FORMAT,getTimeFiveHourAhead(currentTime))}"
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (failedCallOverlayView != null) failedCallWindowManager.removeView(failedCallOverlayView)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        this.stopSelf()
    }
}