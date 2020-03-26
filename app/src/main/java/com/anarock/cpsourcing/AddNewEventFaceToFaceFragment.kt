package com.anarock.cpsourcing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.databinding.FragmentAddNewEventFaceToFaceBinding
import com.anarock.cpsourcing.databinding.FragmentAddNewEventFollowUpBinding
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewEventFaceToFaceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNewEventFaceToFaceFragment : Fragment() {

    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()
    private lateinit var binding : FragmentAddNewEventFaceToFaceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_new_event_face_to_face, container, false)
        sharedUtilityViewModel.setToolBarVisibility(true)
        sharedUtilityViewModel.setCustomToolBar(CustomAppBar(android.R.color.white,R.color.anarock_blue))
        sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)
        binding.eventType.setOnClickListener {
            findNavController().navigate(R.id.action_addNewEventFaceToFace_to_addNewEvent)
        }
        return binding.root
    }
}
