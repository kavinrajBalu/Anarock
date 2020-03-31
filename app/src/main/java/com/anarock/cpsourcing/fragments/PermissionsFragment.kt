package com.anarock.cpsourcing.fragments

import android.Manifest
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentPermissionsBinding
import com.anarock.cpsourcing.model.RemoteConfig
import com.anarock.cpsourcing.model.ToolBarTheme
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.utilities.Constants
import com.anarock.cpsourcing.utilities.RemoteConfigUtil
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import com.google.firebase.remoteconfig.ktx.get
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_permissions.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PermissionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PermissionsFragment : DialogFragment() {
    private val IGNORE_OPTIMIZATION_REQUEST = 100

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //TODO : All runtime permission goes here
    private var permissions = ArrayList<String?>()
    private val REQUEST_MULTIPLE_PERMISSION = 1
    private val loginSharedViewModel: LoginSharedViewModel by activityViewModels()
    var setFullScreen = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if (arguments != null) {
            setFullScreen = arguments!!.getBoolean("fullScreen")
        }

        if (setFullScreen) setStyle(
            STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentPermissionsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_permissions, container, false)

        var remoteConfig = RemoteConfigUtil.getRemoteConfig(requireContext())
        loginSharedViewModel.setToolbarTheme(ToolBarTheme(true, true))

        getAllRequiredPermission()

        try {
            val pm = requireActivity().getSystemService(Context.POWER_SERVICE) as PowerManager
            val isIgnoringBatteryOptimizations: Boolean =
                pm.isIgnoringBatteryOptimizations(requireContext().packageName)
            if (!isIgnoringBatteryOptimizations) {
                val intent =
                    Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:" + requireContext().packageName)
                startActivityForResult(intent, IGNORE_OPTIMIZATION_REQUEST)
            }
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }

        binding.grantPermission.setOnClickListener {
            if (permissions.size > 0)
                requestPermissions(
                    permissions.toArray(arrayOfNulls(permissions.size)),
                    REQUEST_MULTIPLE_PERMISSION
                )
            else
                dismiss()
//                findNavController().navigate(R.id.action_permissionsFragment_to_loginNavigation)

        }

        binding.install.setOnClickListener {
            val gson = Gson()
            val configJson = remoteConfig[Constants.CONNECT_APP_KEY].asString()
            val remoteConfig: RemoteConfig = gson.fromJson(configJson, RemoteConfig::class.java)
            println("url: " + remoteConfig.latestAppUrl)
            if (CommonUtilities.notnull(remoteConfig.latestAppUrl)) {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(remoteConfig.latestAppUrl))
                startActivity(browserIntent)
            }
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }


        return binding.root

    }

    override fun onResume() {
        super.onResume()
        getAllRequiredPermission()
        val isInstalled =
            CommonUtilities.isPackageInstalled(Constants.CONNECT_APP_PACKAGE_NAME, requireContext())
        if (!isInstalled)
            showInstallScreen()
        else if (permissions.size > 0)
            showPermissionScreen()
        else {
            dismiss()
        }
//            dismiss()
//            findNavController().navigate(R.id.action_permissionsFragment_to_loginNavigation)


    }


    private fun showInstallScreen() {
        install.visibility = View.VISIBLE
        grant_permission.visibility = View.GONE
        permission_title.text = getString(R.string.install_sales_connect)
        permission_sub_text.text = getString(R.string.install_sales_connect_text)
        permission_image.setImageResource(R.drawable.ic_install_now_icn)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == IGNORE_OPTIMIZATION_REQUEST) {
            val pm = requireActivity().getSystemService(Context.POWER_SERVICE) as PowerManager
            val isIgnoringBatteryOptimizations =
                pm!!.isIgnoringBatteryOptimizations(context?.packageName)
            if (isIgnoringBatteryOptimizations) {
                // Ignoring battery optimization
            } else {
                // Not ignoring battery optimization
            }
        }
    }

    private fun showPermissionScreen() {
        install.visibility = View.GONE
        grant_permission.visibility = View.VISIBLE
        permission_title.text = getString(R.string.permission_required)
        permission_sub_text.text = getString(R.string.enable_full_functionality)
        permission_image.setImageResource(R.drawable.ic_permission_icn)

    }

    private fun getAllRequiredPermission() {
        permissions.clear()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)


        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.CALL_PHONE)

        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.RECORD_AUDIO)

        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_CONTACTS)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissionsList: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_MULTIPLE_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    findNavController().navigate(R.id.action_permissionsFragment_to_loginNavigation)
                    dismiss()
                } else {
                    // permission denied
                }
            }
            else -> {

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PermissionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PermissionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
