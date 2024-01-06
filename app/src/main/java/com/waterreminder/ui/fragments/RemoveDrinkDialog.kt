package com.waterreminder.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.waterreminder.databinding.DialogRemoveDrinkBinding
import com.waterreminder.ui.viewModels.ReminderViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RemoveDrinkDialog: DialogFragment() {

    private lateinit var binding : DialogRemoveDrinkBinding
    private val mReminderViewModel by sharedViewModel<ReminderViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogRemoveDrinkBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
        }.create()

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.remove.setOnClickListener {
            val value = binding.value.text.toString()
            if(value.isNotBlank()){
                mReminderViewModel.removeDrink(value.toInt())
                dismiss()
            }
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }
    }


}