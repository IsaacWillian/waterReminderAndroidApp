package com.waterreminder.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.waterreminder.R
import com.waterreminder.databinding.DialogReviewBinding
import com.waterreminder.utils.changeTextWithFadeOutFadeIn
import com.waterreminder.utils.fadeIn
import com.waterreminder.utils.fadeOut
import com.waterreminder.utils.invisible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReviewDialog: DialogFragment() {
    private lateinit var binding: DialogReviewBinding

    private var starsNotBlocked = true

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogReviewBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
        }.create()

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        binding.star1.setOnClickListener {
            if(starsNotBlocked) {
                starsNotBlocked = false
                binding.star1.setColorFilter(requireContext().getColor(R.color.stars))
                binding.value.changeTextWithFadeOutFadeIn(requireContext().getString(R.string.review_sorry_text))
                showCancelButton()
            }
        }
        binding.star2.setOnClickListener {
            if(starsNotBlocked) {
                starsNotBlocked = false
                binding.star1.setColorFilter(requireContext().getColor(R.color.stars))
                binding.star2.setColorFilter(requireContext().getColor(R.color.stars))
                binding.value.changeTextWithFadeOutFadeIn(requireContext().getString(R.string.review_sorry_text))
                showCancelButton()
            }
        }
        binding.star3.setOnClickListener {
            if(starsNotBlocked) {
                starsNotBlocked = false
                binding.star1.setColorFilter(requireContext().getColor(R.color.stars))
                binding.star2.setColorFilter(requireContext().getColor(R.color.stars))
                binding.star3.setColorFilter(requireContext().getColor(R.color.stars))
                binding.value.changeTextWithFadeOutFadeIn(requireContext().getString(R.string.review_sorry_text))
                showCancelButton()
            }
        }
        binding.star4.setOnClickListener {
            if(starsNotBlocked) {
                starsNotBlocked = false
                binding.star1.setColorFilter(requireContext().getColor(R.color.stars))
                binding.star2.setColorFilter(requireContext().getColor(R.color.stars))
                binding.star3.setColorFilter(requireContext().getColor(R.color.stars))
                binding.star4.setColorFilter(requireContext().getColor(R.color.stars))
                showReviewButton()
            }
        }
        binding.star5.setOnClickListener {
            if(starsNotBlocked){
                starsNotBlocked = false
            binding.star1.setColorFilter(requireContext().getColor(R.color.stars))
            binding.star2.setColorFilter(requireContext().getColor(R.color.stars))
            binding.star3.setColorFilter(requireContext().getColor(R.color.stars))
            binding.star4.setColorFilter(requireContext().getColor(R.color.stars))
            binding.star5.setColorFilter(requireContext().getColor(R.color.stars))
            showReviewButton()
            }
        }

    }

    private fun showCancelButton(){
        binding.reviewStore.setText(requireContext().getString(R.string.cancel))
        binding.reviewStore.setOnClickListener {
            dismiss()
        }
        binding.reviewStore.fadeIn()
    }

    private fun showReviewButton(){
        binding.reviewStore.setText(requireContext().getString(R.string.review_in_store))
        binding.reviewStore.setOnClickListener {
            launchReviewBottomSheet()
        }
        binding.reviewStore.fadeIn()
    }

    private fun launchReviewBottomSheet(){
        val manager = ReviewManagerFactory.create(requireContext())
        manager.let {
            val request = it.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.let { info ->
                        val flow = it.launchReviewFlow(requireActivity(), info)
                        flow.addOnCompleteListener { _ ->
                            dismiss()
                        }
                    }
                } else {
                        Log.e(
                            "REVIEW",
                            "Request review flow failed ${task.exception?.localizedMessage}"
                        )
                    }

                }
            }
        }


}