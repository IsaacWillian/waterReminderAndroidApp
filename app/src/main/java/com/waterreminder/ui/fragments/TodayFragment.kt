package com.waterreminder.ui.fragments

import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.waterreminder.R
import com.waterreminder.databinding.FragmentTodayBinding
import com.waterreminder.models.Drink
import com.waterreminder.ui.ReminderViewModel
import com.waterreminder.ui.adapter.DrinkListAdapter
import com.waterreminder.utils.changeTextWithFadeOutFadeIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TodayFragment : Fragment() {

    private val mReminderViewModel by sharedViewModel<ReminderViewModel>()
    private var _binding:FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private val TAG = "TodayFragment"
    private var mInterstitialAd: InterstitialAd? = null

    val SECTION_NAME = "WATER"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(), "ca-app-pub-3521984508775017/6842847850", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    setupFullScreenCallback()
                    Log.i(TAG, "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d(TAG, loadAdError.toString())
                    mInterstitialAd = null
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReminderViewModel.isFirstAccess.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_todayFragment_to_firstPageOnboarding)
            }
        }

        mReminderViewModel.motivationalPhrase.observe(viewLifecycleOwner){
            binding.motivationPhrase.changeTextWithFadeOutFadeIn(it)
        }

        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
        val color = ContextCompat.getColor(requireContext(), typedValue.resourceId)
        val section1 = DonutSection(
            name = SECTION_NAME,
            color = color,
            amount = 1f
        )
        binding.progressBar.submitData(listOf(section1))

        val adapter = DrinkListAdapter(requireContext()){drink -> drinkClicked(drink)}

        binding.recyclerDrinks.adapter = adapter
        binding.recyclerDrinks.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        adapter.submitList(listOf(Drink(1,75), Drink(2,100),
            Drink(3,150),Drink
        (4,250),Drink(5,300),Drink(6,350),Drink(7,400),Drink(8,500)))

        mReminderViewModel.waterOfDay.observe(viewLifecycleOwner){
            binding.progressBar.cap = it.second.toFloat()
            binding.progressBar.changeProgressAnimation(it.first)
            binding.waterOfDay.text = it.first.toString()
            binding.goalOfDay.text = it.second.toString()
            lifecycleScope.launch {
                delay(500L)
                if(it.first > 0) {
                    showDialogsAdsOrNothing()
                }
            }
        }

        mReminderViewModel.userName?.observe(viewLifecycleOwner){
            if(it.isNotBlank()){
                binding.greeting.text = requireContext().getString(R.string.greeting, it)
            } else {
                binding.greeting.text = requireContext().getString(R.string.greeting_without_name)
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

        binding.greeting.setOnClickListener {
            val action = TodayFragmentDirections.toWelcomeFragment(false)
            findNavController().navigate(action)
        }



    }

    fun setupFullScreenCallback(){
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }



    fun drinkClicked(drink:Drink) = mReminderViewModel.drink(drink)


    fun showDialogsAdsOrNothing(){
        when((0..10).random()){
            0 -> {
                try {
                    val action = TodayFragmentDirections.actionTodayFragmentToRecommendationDialog()
                    findNavController().navigate(action)
                } catch (_:Exception){

                }
            }
            1,2,3,4,5,6,7,8 -> {
                mInterstitialAd?.show(requireActivity())
            }

            9 -> {
                // nothing
            }
        }
    }

    fun DonutProgressView.changeProgressAnimation(percent: Int){
        val percentToSet = if(percent == 0) 1f else percent.toFloat()
        this.setAmount(SECTION_NAME,percentToSet)
    }
}