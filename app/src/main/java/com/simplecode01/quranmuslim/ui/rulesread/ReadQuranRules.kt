package com.simplecode01.quranmuslim.ui.rulesread

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.control.RepeatMode
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.FragmentReadQuranBinding
import com.simplecode01.quranmuslim.databinding.FragmentReadQuranRulesBinding
import com.simplecode01.quranmuslim.font.QuranArabicUtils
import com.simplecode01.quranmuslim.save.SaveSharedPreferences

class ReadQuranRules : Fragment(R.layout.fragment_read_quran_rules) {
    val binding: FragmentReadQuranRulesBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(SaveSharedPreferences(requireContext()).ganti_font){
            20 ->{
                binding.tvGhun.textSize = 20F
                binding.tvIdhgam.textSize = 20F
                binding.tvIkhfa.textSize = 20F
                binding.tvIqlab.textSize = 20F
                binding.tvQalqalah.textSize = 20F
            }
            30 ->{
                binding.tvGhun.textSize = 30F
                binding.tvIdhgam.textSize = 30F
                binding.tvIkhfa.textSize = 30F
                binding.tvIqlab.textSize = 30F
                binding.tvQalqalah.textSize = 30F
            }
            40 ->{
                binding.tvGhun.textSize = 40F
                binding.tvIdhgam.textSize = 40F
                binding.tvIkhfa.textSize = 40F
                binding.tvIqlab.textSize = 40F
                binding.tvQalqalah.textSize = 40F
            }
            50 ->{
                binding.tvGhun.textSize = 50F
                binding.tvIdhgam.textSize = 50F
                binding.tvIkhfa.textSize = 50F
                binding.tvIqlab.textSize = 50F
                binding.tvQalqalah.textSize = 50F
            }
        }

        binding.tvGhun.text = QuranArabicUtils.getTajweed(context, getString(R.string.gunnah))
        binding.tvIdhgam.text = QuranArabicUtils.getTajweed(context, getString(R.string.idhgam))
        binding.tvIkhfa.text = QuranArabicUtils.getTajweed(context, getString(R.string.ikhfa))
        binding.tvIqlab.text = QuranArabicUtils.getTajweed(context, getString(R.string.iqlab))
        binding.tvQalqalah.text = QuranArabicUtils.getTajweed(context, getString(R.string.qalqalah))

        setUpAudioConfig()
        binding.btnPlayGhunnah.setOnClickListener {
            val audio = SongInfo()
            audio.songName = "Qs. An-Nasr: 3"
            audio.songUrl = "https://archive.org/download/quran-every-ayah/Abdullah%20Basfar_HQ.zip/110003.mp3"
            audio.artist = "Abdullah Ibn Ali Basfar"
            audio.songId = "1"

            StarrySky.with().playMusicByInfo(audio)
        }
        binding.btnPlayQalqalah.setOnClickListener {
            val audio = SongInfo()
            audio.songName = "Qs. Al-Ikhlas: 1"
            audio.songUrl = "https://archive.org/download/quran-every-ayah/Abdullah%20Basfar_HQ.zip/112001.mp3"
            audio.artist = "Abdullah Ibn Ali Basfar"
            audio.songId = "2"

            StarrySky.with().playMusicByInfo(audio)
        }
        binding.btnPlayIqlab.setOnClickListener {
            val audio = SongInfo()
            audio.songName = "Qs. Maryam: 59"
            audio.songUrl = "https://archive.org/download/quran-every-ayah/Abdullah%20Basfar_HQ.zip/019059.mp3"
            audio.artist = "Abdullah Ibn Ali Basfar"
            audio.songId = "3"

            StarrySky.with().playMusicByInfo(audio)
        }
        binding.btnPlayIdgham.setOnClickListener {
            val audio = SongInfo()
            audio.songName = "Qs. Az-Zukhruf: 12"
            audio.songUrl = "https://archive.org/download/quran-every-ayah/Abdullah%20Basfar_HQ.zip/043012.mp3"
            audio.artist = "Abdullah Ibn Ali Basfar"
            audio.songId = "4"

            StarrySky.with().playMusicByInfo(audio)
        }
        binding.btnPlayIkhfa.setOnClickListener {
            val audio = SongInfo()
            audio.songName = "Qs. As-Shura: 10"
            audio.songUrl = "https://archive.org/download/quran-every-ayah/Abdullah%20Basfar_HQ.zip/042010.mp3"
            audio.artist = "Abdullah Ibn Ali Basfar"
            audio.songId = "5"

            StarrySky.with().playMusicByInfo(audio)
        }


    }

    private fun setUpAudioConfig() = StarrySky.with().apply{
        StarrySky.openNotification()
        setRepeatMode(RepeatMode.REPEAT_MODE_NONE, false)
    }
}