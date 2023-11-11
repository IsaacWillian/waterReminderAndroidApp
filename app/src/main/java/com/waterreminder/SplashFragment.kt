package com.waterreminder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.waterreminder.databinding.FragmentSplashBinding
import com.waterreminder.datastore.DataStoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class SplashFragment : Fragment() {

    private val mDataStoreRepository: DataStoreRepository by inject()
    private val TAG = "SplashFragment"
    private var mInterstitialAd: InterstitialAd? = null
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
        viewLifecycleOwner.lifecycleScope.launch {
            val firstAccess = mDataStoreRepository.firstAccess.first()
            if(firstAccess){
                findNavController().navigate(R.id.action_firstFragment_to_firstPageOnboarding)
            } else {
                val adRequest = AdRequest.Builder().build()

                val adId = if(BuildConfig.DEBUG){
                    "ca-app-pub-3940256099942544/1033173712"
                } else {
                    "ca-app-pub-3521984508775017/6842847850"
                }
                InterstitialAd.load(requireContext(),adId, adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd
                            setupFullScreenCallback()
                            interstitialAd.show(requireActivity())
                            Log.i(TAG, "onAdLoaded")
                        }

                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            // Handle the error
                            Log.d(TAG, loadAdError.toString())
                            mInterstitialAd = null
                            goToTodayFragment()
                        }
                    })
            }
        }
    }

    fun goToTodayFragment(){
        findNavController().navigate(R.id.action_firstFragment_to_todayFragment)
    }

    fun setupFullScreenCallback(){
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
                goToTodayFragment()
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                goToTodayFragment()
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }

}