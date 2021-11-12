package com.simplecode01.quranmuslim.ui.about

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.data.QuranDatabase
import com.simplecode01.quranmuslim.dataapiretro.apiPrayer.PrayerAPI
import com.simplecode01.quranmuslim.databinding.FragmentBookmarkBinding
import com.simplecode01.quranmuslim.ui.bookmark.BookmarkAdapter
import com.simplecode01.quranmuslim.ui.quran.QuranViewModel
import com.simplecode01.quranmuslim.ui.quran.ReadQuranFragment
import kotlinx.coroutines.launch
import mumayank.com.airlocationlibrary.AirLocation
import java.util.ArrayList

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {
    private val binding: FragmentBookmarkBinding by viewBinding()
    private val viewModel: QuranViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val database : QuranDatabase = QuranDatabase.getInstance(requireContext())
        val quranDao = database.quranDao()
        quranDao.getBookmark().asLiveData().observe(viewLifecycleOwner, { listBookmark->

            val adapter: BookmarkAdapter = BookmarkAdapter(listBookmark){

            }
            val isFromBookmark = true

            adapter.layoutbookmark ={listBookmark ->
                val bundle = bundleOf(ReadQuranFragment.KEY_SURAH_NUMBER to listBookmark.surahNumber
                    , ReadQuranFragment.KEY_SURAH_NAME to listBookmark.nameSurah
                    , ReadQuranFragment.KEY_SCROLL_TO_POSITION_BOOK to listBookmark.ayahNumber-1, ReadQuranFragment.KEY_FROM_BOOKMARK to isFromBookmark) //kirim data
                findNavController().navigate(R.id.action_nav_bookmark_to_nav_read_quran, bundle)
                viewModel.setTabPosisition(ReadQuranFragment.TAB_SURAH)
            }

            binding.recyclerview.adapter = adapter
            adapter.deleteOnClickListener ={listBookmark ->
                lifecycleScope.launch {
                    quranDao.deleteBookmark(listBookmark)
                }
                Toast.makeText(requireContext(), "Qs. ${listBookmark.nameSurah} Successfully delete", Toast.LENGTH_SHORT)
                    .show()

            }
        })

//        getBookmark()
    }
    private fun getBookmark(){
        val database : QuranDatabase = QuranDatabase.getInstance(requireContext())
        val quranDao = database.quranDao()
        quranDao.getBookmark().asLiveData().observe(viewLifecycleOwner, { listBookmark->

            val adapter: BookmarkAdapter = BookmarkAdapter(listBookmark){

            }

            adapter.layoutbookmark ={listBookmark ->
                val bundle = bundleOf(ReadQuranFragment.KEY_SURAH_NUMBER to listBookmark.surahNumber
                    , ReadQuranFragment.KEY_SURAH_NAME to listBookmark.nameSurah
                    , ReadQuranFragment.KEY_SCROLL_TO_POSITION_BOOK to listBookmark.ayahNumber-1) //kirim data
                findNavController().navigate(R.id.action_nav_bookmark_to_nav_read_quran, bundle)
                viewModel.setTabPosisition(ReadQuranFragment.TAB_SURAH)
            }

            binding.recyclerview.adapter = adapter
            adapter.deleteOnClickListener ={listBookmark ->
                lifecycleScope.launch {
                    quranDao.deleteBookmark(listBookmark)
                }
                Toast.makeText(requireContext(), "Qs. ${listBookmark.nameSurah} Successfully delete", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }
}
