package com.anarock.cpsourcing.callHandler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.anarock.cpsourcing.R

class FaceToFaceSuccessCallOverlay : Service() {

    private lateinit var successCallWindowManager: WindowManager
    private lateinit var successCallOverlayView: View
    lateinit var  params: WindowManager.LayoutParams

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        successCallOverlayView = View.inflate(ContextThemeWrapper(this, R.style.AppTheme), R.layout.face_to_face_successful,null)
        params  = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER //Initially view will be added to top-left corner

        //Add the view to the window
        successCallWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        successCallWindowManager.addView(successCallOverlayView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (successCallOverlayView != null) successCallWindowManager.removeView(successCallOverlayView)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        this.stopSelf()
    }
}