package com.waterreminder.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.waterreminder.R
import com.waterreminder.databinding.DialogRecommendationBinding
import com.waterreminder.utils.copyToClipBoard

class RecommendationDialog: DialogFragment() {
    private lateinit var binding: DialogRecommendationBinding

    private val playStoreLink = "https://play.google.com/store/apps/details?id=com.isaaclabs.bebaja&hl=pt"


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogRecommendationBinding.inflate(layoutInflater)
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
        binding.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            val message = requireContext().getString(R.string.share_message,playStoreLink)
            val title = requireContext().getString(R.string.share_title)
            intent.putExtra(Intent.EXTRA_TEXT,message)
            try {
                startActivity(Intent.createChooser(intent,title))
            }catch(_:ActivityNotFoundException){

            }
        }

        binding.copyLink.setOnClickListener {
            playStoreLink.copyToClipBoard(requireContext())
        }
    }
}