package com.simplecode01.quranmuslim.ui.quran

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.data.QuranDatabase
import com.simplecode01.quranmuslim.databinding.FragmentAboutUsBinding
import com.simplecode01.quranmuslim.databinding.FragmentQuranBinding
import com.simplecode01.quranmuslim.ui.about.AboutUsFragment
import com.simplecode01.quranmuslim.ui.about.RatingFragment
import com.simplecode01.quranmuslim.ui.contact.ContactFragment

class QuranViewPagerFragment: Fragment(R.layout.fragment_quran) {
    private val binding: FragmentQuranBinding by viewBinding()
    private val tittle = arrayOf("Surah", "Juz", "Page")
    private val fragmentList = listOf(TabSurahFragment(), TabJuzFragment(), TabPageFragment())
    private val viewModel: QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = QuranViewPagerAdapter(this, fragmentList)

        val quranDao = QuranDatabase.getInstance(requireContext()).quranDao()
        quranDao.getLastRead().asLiveData().observe(viewLifecycleOwner, {
            it?.let {lastRead ->
                binding.SurahName.text = "${lastRead.nameSurah}"
                binding.ayahNumber.text = "${lastRead.ayahNumber}"

                binding.lastReadLayout.setOnClickListener { view->

                    val bundle = bundleOf(ReadQuranFragment.KEY_SURAH_NUMBER to lastRead.surahNumber
                        ,ReadQuranFragment.KEY_SURAH_NAME to lastRead.nameSurah
                        ,ReadQuranFragment.KEY_SCROLL_TO_POSITION to lastRead.posisitionScroll) //kirim data
                    findNavController().navigate(R.id.action_nav_quran_to_nav_read_quran, bundle)
                    viewModel.setTabPosisition(ReadQuranFragment.TAB_SURAH)
                }

            }
        })
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setTabPosisition(position)
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = tittle[position]
        }.attach()
    }
}