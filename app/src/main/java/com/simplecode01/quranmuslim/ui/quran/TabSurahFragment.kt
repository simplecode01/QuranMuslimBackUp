package com.simplecode01.quranmuslim.ui.quran

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.data.QuranDatabase
import com.simplecode01.quranmuslim.databinding.FragmentTabSurahBinding

class TabSurahFragment: Fragment(R.layout.fragment_tab_surah) {
    private val binding: FragmentTabSurahBinding by viewBinding()
    private val viewModel: QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manggilSurahDatabase()
    }
    private fun manggilSurahDatabase(){
        val database : QuranDatabase = QuranDatabase.getInstance(requireContext())
        val quranDao = database.quranDao()
        quranDao.getSurah().asLiveData().observe(viewLifecycleOwner, {surahList ->
            viewModel.setTotalAyahList(surahList)
            val adapter: QuranSurahAdapter = QuranSurahAdapter(surahList) {surah ->

                val bundle = bundleOf(
                    ReadQuranFragment.KEY_SURAH_NUMBER to surah.surahNumber,
                    ReadQuranFragment.KEY_SURAH_NAME to surah.surahName)
                findNavController().navigate(R.id.action_nav_quran_to_nav_read_quran, bundle)
            }
            binding.recyclerview.adapter = adapter
        })

    }
}