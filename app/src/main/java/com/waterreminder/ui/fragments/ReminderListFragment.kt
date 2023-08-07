package com.waterreminder.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.waterreminder.R
import com.waterreminder.ui.adapter.ReminderListAdapter
import com.waterreminder.databinding.FragmentReminderListBinding
import com.waterreminder.models.ReminderForRecycle
import com.waterreminder.ui.ReminderViewModel
import com.waterreminder.utils.PermissionsUtils
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReminderList : Fragment() {

    private val mReminderViewModel by sharedViewModel<ReminderViewModel>()
    private var _binding: FragmentReminderListBinding? = null
    private val mPermissionsUtils : PermissionsUtils by inject()
    private val binding get() = _binding!!
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderListBinding.inflate(inflater, container, false)

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                findNavController().navigate(R.id.action_reminderList_to_newReminderFragment)
            }
        }


        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val recycler = binding.remindersRecycler
        val adapter = ReminderListAdapter(requireContext(),
            ::deleteReminderListener,
            ::reminderClicked)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.btnNewReminder.setOnClickListener {
            handlePermissionRequest()


        }

        binding.btnHelp.setOnClickListener {
            val action = ReminderListDirections.toPermissionFragment(false)
           findNavController().navigate(action)
        }

        mReminderViewModel.remindersForRecycler.observe(viewLifecycleOwner) {
            it.let { adapter.submitList(it.toList()) }
        }


    }
    fun handlePermissionRequest(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                findNavController().navigate(R.id.action_reminderList_to_newReminderFragment)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            findNavController().navigate(R.id.action_reminderList_to_newReminderFragment)
        }
    }

    fun deleteReminderListener(reminderForRecycle: ReminderForRecycle) = mReminderViewModel.deleteReminder(reminderForRecycle.reminder)


    fun reminderClicked(reminderForRecycle: ReminderForRecycle) = mReminderViewModel.reminderClicked(reminderForRecycle)



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}