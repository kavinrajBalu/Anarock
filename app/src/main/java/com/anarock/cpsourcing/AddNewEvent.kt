package com.anarock.cpsourcing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.databinding.FragmentAddNewEventBinding
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel

class AddNewEvent : Fragment() {
    private val sharedUtilityViewModel: SharedUtilityViewModel by activityViewModels()
    private lateinit var binding : FragmentAddNewEventBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_new_event, container, false)
        sharedUtilityViewModel.setBottomNavigationVisibility(false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            gotoEventScreen()

        }
        sharedUtilityViewModel.setToolBarVisibility(false)
        sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)

        binding.close.setOnClickListener {
            gotoEventScreen()
        }
        binding.visitProposed.setOnClickListener {
            findNavController().navigate(R.id.action_addNewEvent_to_leadSearchFragment)
        }

        binding.followUp.setOnClickListener {
            findNavController().navigate(R.id.action_addNewEvent_to_addNewEventFollowUpFragment)
        }

        binding.faceToFace.setOnClickListener {
            findNavController().navigate(R.id.action_addNewEvent_to_addNewEventFaceToFace)
        }
        return binding.root
    }

    private fun gotoEventScreen() {
        findNavController().navigate(R.id.action_addNewEvent_to_eventFragement)
        sharedUtilityViewModel.setBottomNavigationVisibility(true)
    }
}
