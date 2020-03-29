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
import com.anarock.cpsourcing.databinding.FragmentAddNewEventFollowUpBinding
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.utilities.DateTimeUtils
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import kotlinx.android.synthetic.main.custom_spinner.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewEventFollowUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNewEventFollowUpFragment : Fragment() {

    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()

    private lateinit var binding : FragmentAddNewEventFollowUpBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private val DATE_FORMAT = "EE, MMM dd - hh:ssaa"
    val myCalendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_new_event_follow_up, container, false)
        sharedUtilityViewModel.setToolBarVisibility(true)
        sharedUtilityViewModel.setCustomToolBar(CustomAppBar(android.R.color.white,
            R.color.anarock_blue
        ))
        sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)
        initViews()
        binding.eventType.setOnClickListener {
            findNavController().navigate(R.id.action_addNewEventFollowUpFragment_to_addNewEvent)
        }
        binding.dateTime.setOnClickListener {
            if(datePickerDialog.isShowing)
            {
                datePickerDialog.dismiss()
            }
            datePickerDialog.show()
        }

        binding.dateTime.field.spinner.setOnClickListener {
            if(datePickerDialog.isShowing)
            {
                datePickerDialog.dismiss()
            }
            datePickerDialog.show()
        }

        val time = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            myCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
            myCalendar[Calendar.MINUTE] = minute
            binding.dateTime.field.spinner.setText(DateTimeUtils.customDateTimeString(DATE_FORMAT,myCalendar))
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
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun isMandatoryFieldFilled(): Boolean {
        var isSuccess = true
        if( binding.cpSpinner.field.spinner.text.isEmpty())
        {
            binding.cpSpinner.field.error = "CP required"
            isSuccess= false
        }

        if(binding.dateTime.field.spinner.text.isEmpty())
        {
            binding.dateTime.field.error = "Date time required"
            isSuccess= false
        }

        return isSuccess
    }

    private fun clearAllFields() {
        binding.cpSpinner.field.spinner.text.clear()
        binding.dateTime.field.spinner.text.clear()
        binding.reminderChipGroup.clearCheck()
        binding.notes.text.clear()
    }

    private fun initViews() {
        binding.cpSpinner.field.spinner.hint = getString(R.string.select_cp)
        binding.dateTime.field.spinner.hint = getString(R.string.date_time_hint)
    }

}
