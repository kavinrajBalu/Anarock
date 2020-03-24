package com.anarock.cpsourcing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.anarock.cpsourcing.databinding.FragmentAddNewEventProposedBinding
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel

class AddNewEventProposedFragment : Fragment() {

    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()
    private lateinit var binding : FragmentAddNewEventProposedBinding
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_new_event_proposed, container, false)
            sharedUtilityViewModel.setToolBarVisibility(true)
            sharedUtilityViewModel.setCustomToolBar(CustomAppBar(android.R.color.white,R.color.anarock_blue))
            sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)
        return binding.root
    }


}
