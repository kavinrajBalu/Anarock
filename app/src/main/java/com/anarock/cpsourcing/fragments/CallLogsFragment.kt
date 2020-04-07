package com.anarock.cpsourcing.fragments

import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.adapter.CPSearchResultAdapter
import com.anarock.cpsourcing.adapter.CallLogsAdapter
import com.anarock.cpsourcing.databinding.FragmentCallLogsBinding
import com.anarock.cpsourcing.entities.CallLogs
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.utilities.CommonUtilities.Companion.getFinalCallLogsList
import com.anarock.cpsourcing.utilities.DateTimeUtils
import com.anarock.cpsourcing.utilities.DateTimeUtils.getCurrentDate
import com.anarock.cpsourcing.viewModel.CallLogsViewModel
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap


class CallLogsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()
    private lateinit var callLogsViewModel : CallLogsViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var binding : FragmentCallLogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_call_logs, container, false)
        callLogsViewModel = ViewModelProvider(this).get(CallLogsViewModel::class.java)
        sharedUtilityViewModel.setToolBarVisibility(false)
        sharedUtilityViewModel.setCustomStatusBar(R.color.anarock_blue)
        sharedUtilityViewModel.setBottomNavigationVisibility(false)
        //callLogsViewModel.deleteAll()
        val calender = Calendar.getInstance()
        calender.add(Calendar.DATE,1)
     /*   callLogsViewModel.insert(CallLogs(0, calender.timeInMillis.toString(),3, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, calender.timeInMillis.toString(),3, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, Calendar.getInstance().timeInMillis.toString(),4, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, Calendar.getInstance().timeInMillis.toString(),5, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, Calendar.getInstance().timeInMillis.toString(),7, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, Calendar.getInstance().timeInMillis.toString(),10, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, Calendar.getInstance().timeInMillis.toString(),89, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, Calendar.getInstance().timeInMillis.toString(),4, getCurrentDate(),2,"path",false))
        callLogsViewModel.insert(CallLogs(0, Calendar.getInstance().timeInMillis.toString(),5, getCurrentDate(),2,"path",false))*/
        callLogsViewModel.allCallLogs.observe(viewLifecycleOwner, Observer { it ->
            val callLogsByDate = it.groupBy(keySelector = { it.date})
            val finalList= getFinalCallLogsList(callLogsByDate)
            val adapter = CallLogsAdapter(finalList)
            viewManager = LinearLayoutManager(requireContext())
            binding.callLogsRecylerView.layoutManager = viewManager
            binding.callLogsRecylerView.adapter = adapter
        })

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}
