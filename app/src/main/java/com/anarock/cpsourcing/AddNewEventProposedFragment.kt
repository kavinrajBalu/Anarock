package com.anarock.cpsourcing

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.databinding.FragmentAddNewEventProposedBinding
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.viewModel.CreateEventProposedViewModel
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.custom_spinner.view.*
import kotlinx.android.synthetic.main.custom_text_field.view.*
import kotlinx.android.synthetic.main.fragment_add_new_event_proposed.view.*
import java.util.*
import kotlin.math.min
import kotlin.text.clear


class AddNewEventProposedFragment : Fragment() {

    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()
    private val createEventProposedViewModel: CreateEventProposedViewModel by viewModels()
    private lateinit var binding : FragmentAddNewEventProposedBinding
    private lateinit var projectSpinner : Spinner
    private lateinit var datePickerDialog: DatePickerDialog
    val myCalendar = Calendar.getInstance()
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_new_event_proposed, container, false)
            sharedUtilityViewModel.setToolBarVisibility(true)
            sharedUtilityViewModel.setCustomToolBar(CustomAppBar(android.R.color.white,R.color.anarock_blue))
            sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)
            val items = arrayOf("NMas", "NaasY", "NasaC", "NasaD")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
            binding.projectSpinner.field.spinner.setAdapter(adapter)
            binding.cpSpinner.field.spinner.setAdapter(adapter)
            initViews()

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
                binding.dateTime.field.spinner.setText(myCalendar.time.toString())
            }

            val timePickerDialog  = TimePickerDialog(requireContext(),R.style.DialogTheme,time,Calendar.HOUR_OF_DAY,Calendar.MINUTE,false)
            val date =
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    myCalendar[Calendar.YEAR] = year
                    myCalendar[Calendar.MONTH] = monthOfYear
                    myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                    timePickerDialog.show()
                    //updateLabel()
                }

            datePickerDialog = DatePickerDialog(
                requireContext(),R.style.DialogTheme ,date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )


            binding.reminderChipGroup.setOnCheckedChangeListener { chipGroup, id ->
                val chip = chipGroup.findViewById<Chip>(id)
            }

            binding.eventType.setOnClickListener {
                findNavController().navigate(R.id.action_addNewEventProposedFragment_to_addNewEvent)
            }

            binding.clear.setOnClickListener {
                   clearAllFields()
            }
            return binding.root
    }

    private fun clearAllFields() {
        binding.projectSpinner.field.spinner.text.clear()
        binding.cpSpinner.field.spinner.text.clear()
        binding.leadName.field.spinner.text?.clear()
        binding.leadPhoneNumber.customTextInput.editText?.text?.clear()
        binding.dateTime.field.spinner.text.clear()
        binding.reminderChipGroup.clearCheck()
        binding.notes.text.clear()
    }

    private fun initViews() {
        binding.projectSpinner.field.spinner.hint = getString(R.string.select_project)
        binding.cpSpinner.field.spinner.hint = getString(R.string.select_cp)
        binding.leadName.field.spinner.hint = getString(R.string.enter_lead_name)
        binding.leadPhoneNumber.customTextInput.editText?.inputType = InputType.TYPE_CLASS_PHONE
        binding.leadPhoneNumber.customTextInput.editText?.hint = getString(R.string.lead_phone_number)
        binding.dateTime.field.spinner.hint = getString(R.string.date_time_hint)
    }
}
