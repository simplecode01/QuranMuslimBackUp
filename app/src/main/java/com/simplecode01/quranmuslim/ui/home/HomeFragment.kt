package com.simplecode01.quranmuslim.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.FragmentContactBinding
import com.simplecode01.quranmuslim.databinding.FragmentQuranBinding

class HomeFragment : Fragment() {
    private val binding: FragmentContactBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}