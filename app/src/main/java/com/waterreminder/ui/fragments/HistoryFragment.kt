package com.waterreminder.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.waterreminder.R
import com.waterreminder.ui.adapter.DaysListAdapter
import com.waterreminder.ui.adapter.GraphAdapter
import com.waterreminder.databinding.FragmentHistoryBinding
import com.waterreminder.models.History
import com.waterreminder.ui.viewModels.HistoryViewModel
import com.waterreminder.utils.DateUtils.getMonthNameByMonthNumber
import com.waterreminder.utils.changeTextWithFadeOutFadeIn
import com.waterreminder.utils.smoothProgress
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val mHistoryViewModel : HistoryViewModel by viewModel()
    private var _binding:FragmentHistoryBinding?= null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        mHistoryViewModel.updateHistories()
        hideDayHistory()


    }

    fun hideDayHistory(){
        binding.dailyWater.visibility = View.INVISIBLE
        binding.dailyGoal.visibility = View.INVISIBLE
        binding.waterProgressBar.visibility = View.INVISIBLE
        binding.advice.visibility = View.VISIBLE
    }

    fun showDayHistory(){
        binding.dailyWater.visibility = View.VISIBLE
        binding.dailyGoal.visibility = View.VISIBLE
        binding.waterProgressBar.visibility = View.VISIBLE
        binding.advice.visibility = View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val monthOverviewRecycler = binding.chartBars
        val monthOverviewAdapter = GraphAdapter(requireContext())
        monthOverviewRecycler.adapter = monthOverviewAdapter
        monthOverviewRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        val daysListRecycler = binding.daysList
        val daysListAdapter = DaysListAdapter(requireContext()){history -> onDayClicked(history)}
        daysListRecycler.adapter = daysListAdapter
        daysListRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


        mHistoryViewModel.historysByMonth.observe(viewLifecycleOwner){
            it?.let {
                monthOverviewAdapter.submitList(it)
                daysListAdapter.submitList(it)
            }
        }

        mHistoryViewModel.currentMonthAndYear.observe(viewLifecycleOwner){
            binding.currentMonth.changeTextWithFadeOutFadeIn(getMonthNameByMonthNumber(it.first,context))
            binding.currentYear.changeTextWithFadeOutFadeIn(it.second.toString())
        }

        binding.btnPreviousMonth.setOnClickListener {
            mHistoryViewModel.previousMonth()
        }

        binding.btnNextMonth.setOnClickListener {
            mHistoryViewModel.nextMonth()
        }

    }

    fun onDayClicked(history:History){
        binding.dailyGoal.text = requireContext().getString(R.string.water_with_ml,history.goalOfDay)
        binding.dailyWater.text = requireContext().getString(R.string.water_with_ml,history.waterOfDay)
        showDayHistory()
        binding.waterProgressBar.smoothProgress(((history.waterOfDay.toFloat())/(history.goalOfDay.toFloat())*100).toInt())

    }






}