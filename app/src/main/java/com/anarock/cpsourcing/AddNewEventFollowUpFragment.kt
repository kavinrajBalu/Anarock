package com.anarock.cpsourcing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.databinding.FragmentAddNewEventFollowUpBinding
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewEventFollowUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNewEventFollowUpFragment : Fragment() {

    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()

    private lateinit var binding : FragmentAddNewEventFollowUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_new_event_follow_up, container, false)
        sharedUtilityViewModel.setToolBarVisibility(true)
        sharedUtilityViewModel.setCustomToolBar(CustomAppBar(android.R.color.white,R.color.anarock_blue))
        sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)
        binding.eventType.setOnClickListener {
            findNavController().navigate(R.id.action_addNewEventFollowUpFragment_to_addNewEvent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}
