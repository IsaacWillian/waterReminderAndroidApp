package com.waterreminder.ui.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.waterreminder.R
import com.waterreminder.analytics.Analytics
import com.waterreminder.databinding.FragmentTodayBinding
import com.waterreminder.models.Drink
import com.waterreminder.ui.viewModels.ReminderViewModel
import com.waterreminder.ui.adapter.DrinkListAdapter
import com.waterreminder.utils.changeTextWithFadeOutFadeIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


//TODO Separar ReminderViewModel para TodayViewModel
class TodayFragment : Fragment() {

    private val mReminderViewModel by sharedViewModel<ReminderViewModel>()
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!


    val SECTION_NAME = "WATER"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReminderViewModel.isFirstAccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_todayFragment_to_firstPageOnboarding)
            }
        }

        mReminderViewModel.motivationalPhrase.observe(viewLifecycleOwner) {
            binding.motivationPhrase.changeTextWithFadeOutFadeIn(it)
        }

        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(
            androidx.appcompat.R.attr.colorPrimary,
            typedValue,
            true
        )
        val color = ContextCompat.getColor(requireContext(), typedValue.resourceId)
        val section1 = DonutSection(
            name = SECTION_NAME,
            color = color,
            amount = 1f
        )
        binding.progressBar.submitData(listOf(section1))

        val adapter = DrinkListAdapter(requireContext()) { drink -> drinkClicked(drink) }

        binding.recyclerDrinks.adapter = adapter
        binding.recyclerDrinks.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter.submitList(
            listOf(
                Drink(1, 75), Drink(2, 100),
                Drink(3, 150), Drink
                    (4, 250), Drink(5, 300), Drink(6, 350), Drink(7, 400), Drink(8, 500)
            )
        )

        mReminderViewModel.waterOfDay.observe(viewLifecycleOwner) {
            binding.progressBar.cap = it.second.toFloat()
            binding.progressBar.changeProgressAnimation(it.first)
            binding.waterOfDay.changeTextWithFadeOutFadeIn(it.first.toString())
            binding.goalOfDay.text = it.second.toString()
            lifecycleScope.launch {
                delay(500L)
                if (it.first > 0) {
                    showDialogsAdsOrNothing()
                }
            }
        }


        binding.removeDrink.setOnClickListener {
            val action = TodayFragmentDirections.actionTodayFragmentToRemoveDrinkDialog()
            findNavController().navigate(action)
        }


        binding.goalOfDay.setOnClickListener {
            val action = TodayFragmentDirections.toWelcomeFragment(false)
            findNavController().navigate(action)
        }


    }

    fun drinkClicked(drink: Drink) {
        Analytics.sendEvent("drink",mapOf("mls" to drink.size.toString()))
        mReminderViewModel.drink(drink)
    }


    fun showDialogsAdsOrNothing() {
        when ((0..10).random()) {
            0 -> {
                try {
                    val action = TodayFragmentDirections.actionTodayFragmentToRecommendationDialog()
                    findNavController().navigate(action)
                } catch (_: Exception) {

                }
            }
        }
    }

    fun DonutProgressView.changeProgressAnimation(percent: Int) {
        val percentToSet = if (percent == 0) 1f else percent.toFloat()
        this.setAmount(SECTION_NAME, percentToSet)
    }
}