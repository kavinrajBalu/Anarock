package com.anarock.cpsourcing.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentMoreBinding
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoreFragment : Fragment() {
    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMoreBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_more, container, false)
        sharedUtilityViewModel.setBottomNavigationVisibility(true)
        binding.notificationButton.setOnClickListener {
            showDeepLinkNotification()
        }

        binding.callLogs.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_callLogsFragment)
        }
        return binding.root
    }

    private fun showDeepLinkNotification() {

        val deeplinks = listOf<Int>(
            R.id.cpFragment,
            R.id.eventFragement,
            R.id.notification,
            R.id.moreFragment
        )

        val destination  = deeplinks[Random().nextInt(deeplinks.size)]

        val deeplink = findNavController().createDeepLink()
            .setDestination(destination)
            .createPendingIntent()
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "deeplink", "Deep Links", NotificationManager.IMPORTANCE_HIGH)
            )
        }

        val builder = NotificationCompat.Builder(
                context!!, "deeplink")
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Goto ${resources.getResourceEntryName(destination)}")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(deeplink)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
