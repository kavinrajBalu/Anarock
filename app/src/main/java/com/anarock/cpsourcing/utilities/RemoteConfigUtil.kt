package com.anarock.cpsourcing.utilities

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.anarock.cpsourcing.R
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class RemoteConfigUtil {
    companion object {
        fun getRemoteConfig(mContext: Context): FirebaseRemoteConfig {
            val TAG = "FirebaseRemoteConfig"
            FirebaseApp.initializeApp(mContext);
            var remoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
//            remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                    Toast.makeText(
                        mContext, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        mContext, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            return remoteConfig
        }
    }
}