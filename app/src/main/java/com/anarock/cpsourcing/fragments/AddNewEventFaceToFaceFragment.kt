package com.anarock.cpsourcing.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentAddNewEventFaceToFaceBinding
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.utilities.DateTimeUtils
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import kotlinx.android.synthetic.main.custom_date_time_field.view.*
import kotlinx.android.synthetic.main.custom_spinner.view.*
import java.util.*

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
    private lateinit var datePickerDialog: DatePickerDialog
    private val DATE_FORMAT = "EE, MMM dd - hh:ssaa"
    val myCalendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_new_event_face_to_face, container, false)
        sharedUtilityViewModel.setToolBarVisibility(true)
        sharedUtilityViewModel.setCustomToolBar(CustomAppBar(android.R.color.white,
            R.color.anarock_blue
        ))
        sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)
        binding.eventType.setOnClickListener {
            findNavController().navigate(R.id.action_addNewEventFaceToFace_to_addNewEvent)
        }
        initViews()

          binding.dateTime.customTextInput.editText?.setOnClickListener {
            if(datePickerDialog.isShowing)
            {
                datePickerDialog.dismiss()
            }
            datePickerDialog.show()
        }

        val time = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            myCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
            myCalendar[Calendar.MINUTE] = minute
              binding.dateTime.customTextInput.editText?.setText(DateTimeUtils.customDateTimeString(DATE_FORMAT,myCalendar))
        }

        val timePickerDialog  = TimePickerDialog(requireContext(),
            R.style.DialogTheme,time,
            Calendar.HOUR_OF_DAY,
            Calendar.MINUTE,false)
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                timePickerDialog.show()
                //updateLabel()
            }

        datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        binding.clear.setOnClickListener {
            clearAllFields()
        }

        binding.addEvent.setOnClickListener {
            if(isMandatoryFieldFilled())
            {

            }
        }

        return binding.root
    }

    private fun isMandatoryFieldFilled(): Boolean {
        var isSuccess = true
        if( binding.cpSpinner.field.spinner.text.isEmpty())
        {
            binding.cpSpinner.field.error = "CP required"
            isSuccess= false
        }

        if(  binding.dateTime.customTextInput.editText?.text?.isEmpty() == true)
        {
            binding.dateTime.field.error = "Date time required"
            isSuccess= false
        }

        return isSuccess
    }

    private fun clearAllFields() {
        binding.cpSpinner.field.spinner.text.clear()
          binding.dateTime.customTextInput.editText?.text?.clear()
        binding.reminderChipGroup.clearCheck()
        binding.notes.text.clear()
    }

    private fun initViews() {
        binding.cpSpinner.field.spinner.hint = getString(R.string.search_cp)
        binding.dateTime.customTextInput.editText?.hint = getString(R.string.date_time_hint)
    }
}
