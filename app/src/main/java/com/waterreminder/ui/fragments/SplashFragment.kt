package com.waterreminder.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.waterreminder.R
import com.waterreminder.databinding.FragmentSplashBinding
import com.waterreminder.datastore.DataStoreRepository
import com.waterreminder.ui.viewModels.RedirectAction
import com.waterreminder.ui.viewModels.SplashViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment : Fragment() {

    private val viewModel : SplashViewModel by viewModel()
    private var binding: FragmentSplashBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplashBinding.inflate(layoutInflater,container,false)
        return binding!!.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.redirectAction.observe(viewLifecycleOwner) {
            when (it) {
                RedirectAction.RedirectToOnboarding -> goToOnboarding()
                RedirectAction.RedirectToTodayFragment -> goToTodayFragment()
            }
        }
    }


    fun goToTodayFragment() = findNavController().navigate(R.id.action_firstFragment_to_todayFragment)

    fun goToOnboarding() = findNavController().navigate(R.id.action_firstFragment_to_firstPageOnboarding)



}