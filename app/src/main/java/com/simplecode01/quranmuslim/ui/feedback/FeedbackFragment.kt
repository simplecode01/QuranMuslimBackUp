package com.simplecode01.quranmuslim.ui.feedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.FragmentFeedbackBinding

class FeedbackFragment: Fragment(R.layout.fragment_feedback){
    private val binding: FragmentFeedbackBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var editTextHello = view.findViewById<EditText>(R.id.getMessage)

        binding.sendEmailBtn.setOnClickListener {

            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            selectorIntent.data = Uri.parse("mailto:")

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("quranmuslimcode@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "${editTextHello.text}")
            emailIntent.selector = selectorIntent

            requireActivity().startActivity(Intent.createChooser(emailIntent, "Send email..."))

        }

    }

}
