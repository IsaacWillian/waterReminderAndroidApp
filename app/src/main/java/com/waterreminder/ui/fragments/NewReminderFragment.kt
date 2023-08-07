package com.waterreminder.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.waterreminder.R
import com.waterreminder.ui.ReminderViewModel
import com.waterreminder.databinding.FragmentNewReminderBinding
import com.waterreminder.models.Reminder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewReminderFragment : Fragment() {

    private val mReminderViewModel by sharedViewModel<ReminderViewModel>()
    private var _binding: FragmentNewReminderBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timePicker.setIs24HourView(true)
        binding.btnAdd.setOnClickListener {
            val hour = binding.timePicker.hour
            val minutes = binding.timePicker.minute
            mReminderViewModel.saveReminder(Reminder(0, hour, minutes))
            val toast = Toast.makeText(requireContext(),requireContext().getString(R.string.reminder_succefull),Toast.LENGTH_LONG)
            toast.show()
            findNavController().popBackStack()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewReminderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}