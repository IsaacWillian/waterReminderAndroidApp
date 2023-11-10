package com.waterreminder.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.waterreminder.R
import com.waterreminder.databinding.FragmentFirstPageOnboardingBinding
import com.waterreminder.utils.changeTextWithFadeOutFadeIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FirstPageOnboarding : Fragment() {


    private var _binding : FragmentFirstPageOnboardingBinding? = null
    private val binding get() = _binding!!
    private val features = arrayOf(R.string.features_1,R.string.features_2,R.string.features_3)
    private var features_index = 1
    private var timeToChangeFeature = 2_000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstPageOnboardingBinding.inflate(inflater,container,false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNextPage.setOnClickListener {
            val action = FirstPageOnboardingDirections.actionFirstPageOnboardingToScheduleReminderFragment()
            findNavController().navigate(action)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            while(true){
                delay(timeToChangeFeature)
                binding.features.changeTextWithFadeOutFadeIn(getString(features[features_index%features.size]))
                features_index++
            }
        }

    }


}