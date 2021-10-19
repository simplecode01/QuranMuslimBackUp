package com.simplecode01.quranmuslim.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.FragmentSettingsBinding
import com.simplecode01.quranmuslim.save.SaveSharedPreferences
import com.simplecode01.quranmuslim.utils.TinyDB

class SettingFragment: Fragment(R.layout.fragment_settings)  {
    private val binding: FragmentSettingsBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val tinyDB: TinyDB = TinyDB(requireContext())

        when(SaveSharedPreferences(requireContext()).darkMode){
            1 -> {
                binding.darkLighModeSwitch.isChecked = true
            }
            0 -> {
                binding.darkLighModeSwitch.isChecked = false
            }
        }

        binding.darkLighModeSwitch.setOnClickListener{

            if (binding.darkLighModeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SaveSharedPreferences(requireContext()).darkMode = 1
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SaveSharedPreferences(requireContext()).darkMode = 0
            }

        }
        when(SaveSharedPreferences(requireContext()).ganti_font){
            10 ->{
                binding.tvBismillah.textSize = 10F
                binding.textSize.text = "10"
            }
            20 ->{
                binding.tvBismillah.textSize = 20F
                binding.textSize.text = "20"
            }
            30 ->{
                binding.tvBismillah.textSize = 30F
                binding.textSize.text = "30"
            }
            40 ->{
                binding.tvBismillah.textSize = 40F
                binding.textSize.text = "40"
            }
        }

        when(SaveSharedPreferences(requireContext()).ganti_qori){
            0 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Syeikh Abdurrahman As-Sudais"
            }
            1 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Abdullah Ibn Ali Basfar"
            }
            2 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Sheikh Abu Bakr Al Shatri"
            }
            3 ->{
                binding.textQori.text = "Ahmad bin Ali Al-Ajmi"
            }
            4 ->{
                binding.textQori.text = "Shaykh Mishari Alafasy"
            }
        }

        binding.layoutText.setOnClickListener {
            val items = arrayOf("10","20" ,"30","40")
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("Text Size")
                setItems(items) { dialog, which ->
                    Toast.makeText(requireContext(), "Size Changes to : " + items[which], Toast.LENGTH_SHORT).show()
                    when(items[which]){
                        "10" ->{
                            val fontSize: Float = 10F
                            SaveSharedPreferences(requireContext()).ganti_font = 10
                            binding.tvBismillah.textSize = fontSize
                            binding.textSize.text = "10"
                        }
                        "20" ->{
                            val fontSize: Float = 20F
                            binding.tvBismillah.textSize = fontSize
                            SaveSharedPreferences(requireContext()).ganti_font = 20
                            binding.textSize.text = "20"
                        }
                        "30" ->{
                            val fontSize: Float = 30F
                            binding.tvBismillah.textSize = fontSize
                            SaveSharedPreferences(requireContext()).ganti_font = 30
                            binding.textSize.text = "30"
                        }
                        "40" ->{
                            val fontSize: Float = 40F
                            binding.tvBismillah.textSize = fontSize
                            SaveSharedPreferences(requireContext()).ganti_font = 40
                            binding.textSize.text = "40"
                        }
                    }
                }
                show()
            }
        }

        binding.layoutQori.setOnClickListener {
//            val items = arrayOf("Syeikh Abdurrahman As-Sudais","Abdullah Ibn Ali Basfar" ,"Sheikh Abu Bakr Al Shatri","Ahmad bin Ali Al-Ajmi", "Shaykh Mishari Alafasy")
//            val builder = AlertDialog.Builder(requireContext())

//            with(builder) {
//                setTitle("Change Qori")
//                setItems(items) { dialog, which ->
//                    Toast.makeText(requireContext(), "Qori Changes to : " + items[which], Toast.LENGTH_SHORT).show()
//                    when(items[which]){
//                        "Syeikh Abdurrahman As-Sudais" ->{
//                            SaveSharedPreferences(requireContext()).ganti_qori = 0
//                            binding.textQori.text = "Syeikh Abdurrahman As-Sudais"
//                        }
//                        "Abdullah Ibn Ali Basfar" ->{
//                            SaveSharedPreferences(requireContext()).ganti_qori = 1
//                            binding.textQori.text = "Abdullah Ibn Ali Basfar"
//                        }
//                        "Sheikh Abu Bakr Al Shatri" ->{
//                            SaveSharedPreferences(requireContext()).ganti_qori = 2
//                            binding.textQori.text = "Sheikh Abu Bakr Al Shatri"
//                        }
//                        "Ahmad bin Ali Al-Ajmi" ->{
//                            SaveSharedPreferences(requireContext()).ganti_qori = 3
//                            binding.textQori.text = "Ahmad bin Ali Al-Ajmi"
//                        }
//                        "Shaykh Mishari Alafasy" ->{
//                            SaveSharedPreferences(requireContext()).ganti_qori = 4
//                            binding.textQori.text = "Shaykh Mishari Alafasy"
//                        }
//                    }
//                }
//                show()
//            }
        }
    }
}