package com.waterreminder.ui.fragments

import android.Manifest
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.waterreminder.R
import com.waterreminder.databinding.FragmentScheduleReminderBinding
import com.waterreminder.ui.ReminderViewModel
import com.waterreminder.utils.DateUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ScheduleReminderFragment : Fragment() {

    private lateinit var binding: FragmentScheduleReminderBinding
    private val viewModel by sharedViewModel<ReminderViewModel>()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScheduleReminderBinding.inflate(inflater,container, false)

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
               makeReminders()
            } else {
                findNavController().navigate(R.id.action_scheduleReminderFragment_to_todayFragment)
            }
        }

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
            handlePermissionRequest()
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


    fun handlePermissionRequest(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                makeReminders()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            makeReminders()
        }
    }

    private fun makeReminders(){
        val intervalValue = binding.intervalPicker.displayedValues[binding.intervalPicker.value-1].toInt()
        viewModel.createRemindersWithStartAndFinish(binding.startTimePicker.text.toString(),binding.finishTimePicker.text.toString(),intervalValue)
        findNavController().navigate(R.id.action_scheduleReminderFragment_to_todayFragment)
    }




}