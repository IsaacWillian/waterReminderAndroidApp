package com.waterreminder.ui.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import androidx.navigation.fragment.findNavController
import com.waterreminder.R
import com.waterreminder.databinding.FragmentScheduleReminderBinding
import com.waterreminder.ui.ReminderViewModel
import com.waterreminder.utils.DateUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ScheduleReminderFragment : Fragment() {

    private lateinit var binding: FragmentScheduleReminderBinding
    private val viewModel by sharedViewModel<ReminderViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScheduleReminderBinding.inflate(inflater,container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startTimePicker.text = DateUtils.getFormattedTime(7,0)
        binding.finishTimePicker.text = DateUtils.getFormattedTime(20,0)
        binding.startTimePicker.setOnClickListener {
            callTimePickerDialog(it as TextView,7,0)
        }
        binding.finishTimePicker.setOnClickListener {
            callTimePickerDialog(it as TextView,20,0)
        }

        binding.intervalPicker.displayedValues = arrayOf("30","60","90","120")

        binding.btnBegin.setOnClickListener {
            val intervalValue = binding.intervalPicker.displayedValues[binding.intervalPicker.value-1].toInt()
            viewModel.createRemindersWithStartAndFinish(binding.startTimePicker.text.toString(),binding.finishTimePicker.text.toString(),intervalValue)
            findNavController().navigate(R.id.action_scheduleReminderFragment_to_todayFragment)
        }

    }

    private fun callTimePickerDialog(textView:TextView, defaulttHour: Int, defaultMinutes:Int){
        val listener = object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                textView.text = DateUtils.getFormattedTime(hour,minute)
            }
        }
        val dialog = TimePickerDialog(requireContext(),listener,defaulttHour,defaultMinutes,true)
        dialog.show()
    }






}