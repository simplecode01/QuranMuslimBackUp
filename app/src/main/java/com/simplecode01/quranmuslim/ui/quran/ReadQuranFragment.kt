package com.simplecode01.quranmuslim.ui.quran

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.control.RepeatMode
import com.lzx.starrysky.manager.PlaybackStage
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.data.QuranDatabase
import com.simplecode01.quranmuslim.databinding.FragmentReadQuranBinding
import com.simplecode01.quranmuslim.model.Bookmark
import com.simplecode01.quranmuslim.model.LastRead
import com.simplecode01.quranmuslim.model.Quran
import com.simplecode01.quranmuslim.save.SaveSharedPreferences
import kotlinx.coroutines.launch
import java.util.*


class ReadQuranFragment: Fragment(R.layout.fragment_read_quran) {
    val binding: FragmentReadQuranBinding by viewBinding()
    private val viewModel: QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        setUpAudioConfig()
        val dataPaketSurahNumber = arguments?.getInt(KEY_SURAH_NUMBER) ?: 1
        val dataSurahName = arguments?.getString(KEY_SURAH_NAME)?: ""
        val dataPaketJuzNumber = arguments?.getInt(KEY_JUZ_NUMBER)?: 1
        val dataPaketPageNumber = arguments?.getInt(KEY_PAGE_NUMBER)?: 1
        val scrollPosition = arguments?.getInt(KEY_SCROLL_TO_POSITION)?: 0
        val isFromBookmark = arguments?.getBoolean(KEY_FROM_BOOKMARK)?: false
        val Bookmark_Posistion = arguments?.getInt(KEY_SCROLL_TO_POSITION_BOOK)?: 0
        Log.d("CEKBOOKMARK", Bookmark_Posistion.toString())

        Log.d("CEKSCROLL", scrollPosition.toString())

        viewModel.getTabPosisition().observe(viewLifecycleOwner, { tabPosition ->
            viewModel.getTotalAyahList().observe(viewLifecycleOwner,{totalAyahlist ->
                val database = QuranDatabase.getInstance(requireContext())
                val quranDao = database.quranDao()
                var titleToolBar: String = ""
                when(isFromBookmark){
                    true ->{
                        quranDao.getSurahByNumber(dataPaketSurahNumber).asLiveData().observe(viewLifecycleOwner,{quranList ->
                            setQuranAdapter(quranList, totalAyahlist, Bookmark_Posistion)

                        })
                    }
                    false ->{
                        when(tabPosition){
                            TAB_SURAH ->{
                                quranDao.getSurahByNumber(dataPaketSurahNumber).asLiveData().observe(viewLifecycleOwner, {quranList ->
                                    setQuranAdapter(quranList, totalAyahlist, scrollPosition)
                                })
                                titleToolBar = "Qs. $dataSurahName"
                            }
                            TAB_JUZ ->{
                                quranDao.getJuzByNumber(dataPaketJuzNumber).asLiveData().observe(viewLifecycleOwner, {quranList ->
                                    setQuranAdapter(quranList, totalAyahlist, scrollPosition)
                                })
                                titleToolBar = "Juz $dataPaketJuzNumber"
                            }
                            TAB_PAGE ->{
                                quranDao.getPageByNumber(dataPaketPageNumber).asLiveData().observe(viewLifecycleOwner, {quranList ->
                                    setQuranAdapter(quranList, totalAyahlist, scrollPosition)
                                })
                                titleToolBar = "Page $dataPaketPageNumber"
                            }
                        }
                    }
                }
                val toolbarActivity = requireActivity().findViewById<Toolbar>(R.id.toolbar)
                toolbarActivity.title = titleToolBar
            })
        })
    }

    private fun setUpAudioConfig() = StarrySky.with().apply{
        StarrySky.openNotification()
        setRepeatMode(RepeatMode.REPEAT_MODE_NONE, false)
    }

    private fun setQuranAdapter(quranList: List<Quran>, ayahTotal: List<Int>, scrollPosition: Int) {
        val adapter: ReadQuranAdapter = ReadQuranAdapter(quranList, ayahTotal)
        binding.recyclerview.adapter = adapter

        Handler().postDelayed({
            if(scrollPosition <= 50){
                binding.recyclerview.smoothScrollToPosition(scrollPosition)
            }else{
                binding.recyclerview.scrollToPosition(scrollPosition)
            }
        },100)





        adapter.footnotesOnClickListener={quran ->
            val bundle = bundleOf(QuranFootnotesFragment.KEY_FOOTNOTES to quran.footnotes)
            findNavController().navigate(R.id.action_nav_read_quran_to_nav_quran_footnotes, bundle)
        }

        adapter.moreCLickListener={quran, totalAyah, position->

            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setCancelable(true)
            dialog.setContentView(view)

            val btnCopy = view.findViewById<LinearLayout>(R.id.copyAyah)
            val btnShare = view.findViewById<LinearLayout>(R.id.shareAyah)
            val btnPlayAyah = view.findViewById<LinearLayout>(R.id.playAyah)
            val btnPlaySurah = view.findViewById<LinearLayout>(R.id.playFullSurah)
            val btnLastRead = view.findViewById<LinearLayout>(R.id.saveLastRead)
            val btnBookmark = view.findViewById<LinearLayout>(R.id.saveBookMark)
            val btnError = view.findViewById<LinearLayout>(R.id.reportError)
            val btnClose = view.findViewById<ImageView>(R.id.close_bottom)

            btnError.setOnClickListener {
                val error = "Quran Ayat : ${quran.textQuran}\n\nQuran Translate : ${quran.translation}\n\n (Q.s ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber})"
                val selectorIntent = Intent(Intent.ACTION_SENDTO)
                selectorIntent.data = Uri.parse("mailto:")

                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("quranmuslimcode@gmail.com"))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Error Report")
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Error Report \n ${error} \n Input Error here")
                emailIntent.selector = selectorIntent

                requireActivity().startActivity(Intent.createChooser(emailIntent, "Send email..."))
            }

            btnCopy.setOnClickListener{
                val copyContent = "Quran Ayat : ${quran.textQuran}\n\nQuran Translate : ${quran.translation}\n\n(Q.S ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber}))"
                val clipboard: ClipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE)as ClipboardManager
                val clip = ClipData.newPlainText("copy_ayah", copyContent)
                clipboard.setPrimaryClip(clip)

                Toast.makeText(requireContext(), "Copy Qs. ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber}", Toast.LENGTH_SHORT)
                    .show();
                dialog.dismiss()
            }
            btnShare.setOnClickListener {
                val shareContent = "Quran Ayat : ${quran.textQuran}\n\nQuran Translate : ${quran.translation}\n\n (Q.s ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber})"
                ShareCompat.IntentBuilder(requireContext())
                    .setText(shareContent)
                    .setType("text/plain")
                    .startChooser()
                Toast.makeText(requireContext(), "Share Qs. ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber}", Toast.LENGTH_SHORT)
                    .show();
                dialog.dismiss()
            }
            btnPlayAyah.setOnClickListener {
                val audioImam = SongInfo()
                val surahNumberFormat = String.format("%03d", quran.surahNumber)
                val ayahNumberFormat = String.format("%03d", quran.ayahNumber)
                var audioURL: String = "" ;
                var imamArtist: String = "";
                var imamCover: String = "";

                when(SaveSharedPreferences(requireContext()).ganti_qori){
                    0 ->{
                        imamArtist = "Abdurrahman As-Sudais"
                        audioURL = "https://archive.org/download/quran-every-ayah/Abdurrahman%20as-Sudais_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                        imamCover = "https://www.madaninews.id/wp-content/uploads/2018/07/Abdul-Rahman-Al-Sudais-at-digital-mode-by-syed-noman-zafar-855x1024.jpg"
                    }
                    1 ->{
                        imamArtist = "Abdullah Ibn Ali Basfar"
                        audioURL = "https://archive.org/download/quran-every-ayah/Abdullah%20Basfar_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                        imamCover = "http://3.bp.blogspot.com/-gTq-PJCtte4/WT9QikiTibI/AAAAAAAARlg/C1S3JCDgiu4TwwBy8TM_bW00ALZ1-siAQCK4B/s1600/Abdullah-Ibn-Ali-Basfar-%25D8%25B9%25D8%25A8%25D8%25AF%2B%25D8%25A7%25D9%2584%25D9%2584%25D9%2587%2B%25D8%25A8%25D9%2586%2B%25D8%25B9%25D9%2584%25D9%258A%2B%25D8%25A8%25D8%25B5%25D9%2581%25D8%25B1.jpg"

                    }
                    2 ->{
                        imamArtist = "Abu Bakr Al Shatri"
                        audioURL = "https://archive.org/download/quran-every-ayah/Abu%20Bakr%20Ashatri_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                        imamCover = "https://i1.sndcdn.com/artworks-000387234564-j0hxuf-t500x500.jpg"
                    }
                    3 ->{
                        imamArtist = "bin Ali Al-Ajmi"
                        audioURL = "https://archive.org/download/quran-every-ayah/Ahmed%20ibn%20Ali%20Al-Ajamy_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                        imamCover = "https://alquranonline.net/wp-content/uploads/2021/06/Assabile-Ahmed-Al-Ajmi-FULL-MP3.jpg"
                    }
                    4 ->{
                        imamArtist = "Mishari Alafasy"
                        audioURL = "https://archive.org/download/quran-every-ayah/Mishary%20Rashid%20Alafasy_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                        imamCover = "https://upload.wikimedia.org/wikipedia/commons/2/24/%D0%9C%D0%B8%D1%88%D0%B0%D1%80%D0%B8_%D0%A0%D0%B0%D1%88%D0%B8%D0%B4.jpg"
                    }
                    5 ->{
                        imamArtist = "Ali Jaber"
                        audioURL = "https://archive.org/download/quran-every-ayah/Ali%20Jaber.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                        imamCover = "https://assets.pikiran-rakyat.com/crop/0x0:0x0/360x192/photo/2021/01/23/172094177.jpg"
                    }
                }

                audioImam.songName = "Qs. ${quran.surahName}: ${quran.ayahNumber}"
                audioImam.songUrl = audioURL
                audioImam.artist = imamArtist
                audioImam.songId = quran.id.toString()
                audioImam.songCover = imamCover

                StarrySky.with().playMusicByInfo(audioImam)

                Toast.makeText(requireContext(), "Play Qs. ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber}", Toast.LENGTH_SHORT)
                    .show();
                dialog.dismiss()
            }
            btnPlaySurah.setOnClickListener {

                val audioList = mutableListOf<SongInfo>()
                val surahNumberFormat = String.format("%03d", quran.surahNumber)

                for (i in 1..totalAyah) {
                    val audio = SongInfo()
                    val ayahNumberFormat = String.format("%03d", i)
                    var audioImam: String = ""
                    var coverImam: String = ""
                    var artistImam: String = ""

                    when(SaveSharedPreferences(requireContext()).ganti_qori) {
                        0 -> {
                            artistImam = "Abdurrahman As-Sudais"
                            audioImam = "https://archive.org/download/quran-every-ayah/Abdurrahman%20as-Sudais_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                            coverImam = "https://www.madaninews.id/wp-content/uploads/2018/07/Abdul-Rahman-Al-Sudais-at-digital-mode-by-syed-noman-zafar-855x1024.jpg"
                        }
                        1 -> {
                            artistImam = "Abdullah Ibn Ali Basfar"
                            audioImam = "https://archive.org/download/quran-every-ayah/Abdullah%20Basfar_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                            coverImam = "http://3.bp.blogspot.com/-gTq-PJCtte4/WT9QikiTibI/AAAAAAAARlg/C1S3JCDgiu4TwwBy8TM_bW00ALZ1-siAQCK4B/s1600/Abdullah-Ibn-Ali-Basfar-%25D8%25B9%25D8%25A8%25D8%25AF%2B%25D8%25A7%25D9%2584%25D9%2584%25D9%2587%2B%25D8%25A8%25D9%2586%2B%25D8%25B9%25D9%2584%25D9%258A%2B%25D8%25A8%25D8%25B5%25D9%2581%25D8%25B1.jpg"

                        }
                        2 -> {
                            artistImam = "Abu Bakr Al Shatri"
                            audioImam = "https://archive.org/download/quran-every-ayah/Abu%20Bakr%20Ashatri_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                            coverImam = "https://i1.sndcdn.com/artworks-000387234564-j0hxuf-t500x500.jpg"
                        }
                        3 -> {
                            artistImam = "Ahmad bin Ali Al-Ajmi"
                            audioImam = "https://archive.org/download/quran-every-ayah/Ahmed%20ibn%20Ali%20Al-Ajamy_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                            coverImam = "https://alquranonline.net/wp-content/uploads/2021/06/Assabile-Ahmed-Al-Ajmi-FULL-MP3.jpg"
                        }
                        4 -> {
                            artistImam = "Mishari Alafasy"
                            audioImam = "https://archive.org/download/quran-every-ayah/Mishary%20Rashid%20Alafasy_HQ.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                            coverImam = "https://upload.wikimedia.org/wikipedia/commons/2/24/%D0%9C%D0%B8%D1%88%D0%B0%D1%80%D0%B8_%D0%A0%D0%B0%D1%88%D0%B8%D0%B4.jpg"
                        }
                        5 ->{
                            artistImam = "Ali Jaber"
                            audioImam = "https://archive.org/download/quran-every-ayah/Ali%20Jaber.zip/$surahNumberFormat$ayahNumberFormat.mp3"
                            coverImam = "https://assets.pikiran-rakyat.com/crop/0x0:0x0/360x192/photo/2021/01/23/172094177.jpg"
                        }
                    }

                    audio.songName = "Qs. ${quran.surahName}: ${i}"
                    audio.songUrl = audioImam
                    audio.songCover = coverImam
                    audio.songId = surahNumberFormat + ayahNumberFormat
                    audio.artist = artistImam
                    audioList.add(audio)
                }
                val indexStartPlay = quran.ayahNumber!! - 1
                StarrySky.with().playMusic(audioList, indexStartPlay)

                StarrySky.with().playbackState().observe(viewLifecycleOwner, { playBackStage ->
                    when (playBackStage.stage) {
                        PlaybackStage.PLAYING -> {
                            binding.txtTitle.text = playBackStage.songInfo?.songName
                        }
                        PlaybackStage.SWITCH -> {
                            playBackStage.songInfo?.let {
                            }
                        }
                        PlaybackStage.PAUSE->{
                            Toast.makeText(requireContext(), "Play Surah Pause", Toast.LENGTH_SHORT).show();
                        }
                        PlaybackStage.IDEA -> {
                            playBackStage.songInfo?.let {
//                                Toast.makeText(requireContext(), "${playBackStage.songInfo?.songName}", Toast.LENGTH_SHORT).show();
                            }
                        }
                        PlaybackStage.ERROR -> {
                            Toast.makeText(requireContext(), "Error" + playBackStage.errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                })


                Toast.makeText(requireContext(), "Play Qs. ${quran.surahName}", Toast.LENGTH_SHORT)
                    .show();

                binding.bottomBar.visibility = View.VISIBLE
                dialog.dismiss()
            }

            btnLastRead.setOnClickListener {
                val lastRead = LastRead(id = null, posisitionScroll = position, nameSurah = quran.surahName!!, ayahNumber = quran.ayahNumber!!, surahNumber = quran.surahNumber!!)
                val database = QuranDatabase.getInstance(requireContext())
                val quranDao = database.quranDao()
                lifecycleScope.launch {
                    quranDao.setLastRead(lastRead)
                    Toast.makeText(requireContext(), "Last read in Qs. ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber}", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss()
            }

            btnBookmark.setOnClickListener {
                val bookmark = Bookmark(id = null, posisitionScrollBookmark = position, nameSurah = quran.surahName!!, ayahNumber = quran.ayahNumber!!, surahNumber = quran.surahNumber!!, timestamp = Date().time)
                val database = QuranDatabase.getInstance(requireContext())
                val quranDao = database.quranDao()
                lifecycleScope.launch {
                    quranDao.addBookmark(bookmark)
                    Toast.makeText(requireContext(), "Bookmark add Qs. ${quran.surahName}[${quran.surahNumber}]: ${quran.ayahNumber}", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss()
            }
            btnClose.setOnClickListener {
                dialog.dismiss()
            }


            dialog.show()
        }
        binding.bottomBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.nav_prev){
                StarrySky.with().skipToPrevious()
            }
            else if(menuItem.itemId == R.id.nav_play){
                if (StarrySky.with().isPlaying()) {
                    StarrySky.with().pauseMusic()
                    menuItem.setIcon(R.drawable.ic_baseline_play_arrow)
                } else {
                    StarrySky.with().restoreMusic()
                    menuItem.setIcon(R.drawable.ic_baseline_pause)
                }
            }
            else if(menuItem.itemId == R.id.nav_next){
                StarrySky.with().skipToNext()
            }
            else if(menuItem.itemId == R.id.nav_loop){
                val model = StarrySky.with().getRepeatMode()
                when (model.repeatMode) {
                    RepeatMode.REPEAT_MODE_NONE -> if (model.isLoop) {
                        StarrySky.with().setRepeatMode(RepeatMode.REPEAT_MODE_NONE, false)
                        Toast.makeText(requireContext(), "Surah loop stop", Toast.LENGTH_SHORT).show();
                    }else {
                        StarrySky.with().setRepeatMode(RepeatMode.REPEAT_MODE_NONE, true)
                        Toast.makeText(requireContext(), "Surah Loop", Toast.LENGTH_SHORT).show();
                        menuItem.setIcon(R.drawable.ic_baseline_repeat)
                    }
                    RepeatMode.REPEAT_MODE_ONE -> if (model.isLoop) {
                        StarrySky.with().setRepeatMode(RepeatMode.REPEAT_MODE_ONE, false)
                        Toast.makeText(requireContext(), "Ayat Loop", Toast.LENGTH_SHORT).show();
                    }else {
                        StarrySky.with().setRepeatMode(RepeatMode.REPEAT_MODE_ONE, true)
                        menuItem.setIcon(R.drawable.ic_baseline_repeat_one)
                    }
                }
            }
            true
        }


    }

    //ini untuk menu untuk fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //ini untuk clicklistenernya
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_search -> {
                //Untuk pindah antar fragment
                findNavController().navigate(R.id.action_nav_read_quran_to_nav_search_quran)
                true
            }
            else ->{
                false
            }
        }
    }

    companion object{
        const val KEY_FROM_BOOKMARK = "Hai"
        const val KEY_SCROLL_TO_POSITION_BOOK = "scroll_position_book"
        const val KEY_SURAH_NAME = "surah_name"
        const val KEY_SURAH_NUMBER = "surah_number"
        const val KEY_SCROLL_TO_POSITION = "scroll_position"
        const val KEY_JUZ_NUMBER = "juz_number"
        const val KEY_PAGE_NUMBER = "page_number"
        const val TAB_SURAH = 0
        const val TAB_JUZ = 1
        const val TAB_PAGE = 2
    }

}