package com.simplecode01.quranmuslim.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.FragmentSettingsBinding
import com.simplecode01.quranmuslim.save.SaveSharedPreferences

class SettingFragment: Fragment(R.layout.fragment_settings)  {
    private lateinit var test: String
    private val binding: FragmentSettingsBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        test = ""


        when(SaveSharedPreferences(requireContext()).darkMode){
            1 -> {
                binding.darkLighModeSwitch.isChecked = true
            }
            0 -> {
                binding.darkLighModeSwitch.isChecked = false
            }
        }

        when(SaveSharedPreferences(requireContext()).translation){
            1 ->{
                binding.translationSwitch.isChecked = true
            }
            0 ->{
                binding.translationSwitch.isChecked = false
            }
        }

        when(SaveSharedPreferences(requireContext()).latin){
            1 ->{
                binding.latinSwitch.isChecked = true
            }
            0 ->{
                binding.latinSwitch.isChecked = false
            }
        }


        binding.translationSwitch.setOnClickListener {
            if(binding.translationSwitch.isChecked){
                SaveSharedPreferences(requireContext()).translation = 1
                Toast.makeText(requireContext(), "Translation text show", Toast.LENGTH_SHORT).show();
            }else{
                SaveSharedPreferences(requireContext()).translation = 0
                Toast.makeText(requireContext(), "Translation text hide", Toast.LENGTH_SHORT).show()
            }
        }

        binding.latinSwitch.setOnClickListener {
            if(binding.latinSwitch.isChecked){
                SaveSharedPreferences(requireContext()).latin = 1
                Toast.makeText(requireContext(), "Latin text show", Toast.LENGTH_SHORT).show();
            }else{
                SaveSharedPreferences(requireContext()).latin = 0
                Toast.makeText(requireContext(), "Latin text hide", Toast.LENGTH_SHORT).show()
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
            50 ->{
                binding.tvBismillah.textSize = 50F
                binding.textSize.text = "50"
            }
        }

        when(SaveSharedPreferences(requireContext()).ganti_qori){
            0 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Abdurrahman As-Sudais"
            }
            1 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Abdullah Ibn Ali Basfar"
            }
            2 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Abu Bakr Al Shatri"
            }
            3 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "bin Ali Al-Ajmi"
            }
            4 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Mishari Alafasy"
            }
            5 ->{
                Log.d("CEKQORIBERUBAH", SaveSharedPreferences(requireContext()).ganti_qori.toString())
                binding.textQori.text = "Ali Jaber"
            }
        }

        binding.layoutText.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.botoomsheet_size_font, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setCancelable(true)
            dialog.setContentView(view)

            val btnSize1 = view.findViewById<LinearLayout>(R.id.size1)
            val btnSize2 = view.findViewById<LinearLayout>(R.id.size2)
            val btnSize3 = view.findViewById<LinearLayout>(R.id.size3)
            val btnSize4 = view.findViewById<LinearLayout>(R.id.size4)

            btnSize1.setOnClickListener {
                val fontSize: Float = 20F
                SaveSharedPreferences(requireContext()).ganti_font = 20
                binding.tvBismillah.textSize = fontSize
                binding.textSize.text = "20"
                Toast.makeText(requireContext(), "Font size set to 20", Toast.LENGTH_SHORT).show();
                dialog.dismiss()
            }
            btnSize2.setOnClickListener {
                val fontSize: Float = 30F
                binding.tvBismillah.textSize = fontSize
                SaveSharedPreferences(requireContext()).ganti_font = 30
                binding.textSize.text = "30"
                Toast.makeText(requireContext(), "Font size set to 30", Toast.LENGTH_SHORT).show();
                dialog.dismiss()
            }
            btnSize3.setOnClickListener {
                val fontSize: Float = 40F
                binding.tvBismillah.textSize = fontSize
                SaveSharedPreferences(requireContext()).ganti_font = 40
                binding.textSize.text = "40"
                Toast.makeText(requireContext(), "Font size set to 40", Toast.LENGTH_SHORT).show();
                dialog.dismiss()
            }
            btnSize4.setOnClickListener {
                val fontSize: Float = 50F
                binding.tvBismillah.textSize = fontSize
                SaveSharedPreferences(requireContext()).ganti_font = 50
                binding.textSize.text = "50"
                Toast.makeText(requireContext(), "Font size set to 50", Toast.LENGTH_SHORT).show();
                dialog.dismiss()
            }

            dialog.show()
        }

        binding.layoutQori.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.bottom_sheet_qori, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setCancelable(true)
            dialog.setContentView(view)

            val btnImam1 = view.findViewById<LinearLayout>(R.id.imam1)
            val btnImam2 = view.findViewById<LinearLayout>(R.id.city2)
            val btnImam3 = view.findViewById<LinearLayout>(R.id.imam3)
            val btnImam4 = view.findViewById<LinearLayout>(R.id.imam4)
            val btnImam5 = view.findViewById<LinearLayout>(R.id.imam5)
            val btnImam6 = view.findViewById<LinearLayout>(R.id.imam6)

            btnImam1.setOnClickListener {
                SaveSharedPreferences(requireContext()).ganti_qori = 0
                binding.textQori.text = "Abdurrahman As-Sudais"
                dialog.dismiss()
                Toast.makeText(requireContext(), "Quran reader is set to Abdurrahman As-Sudais", Toast.LENGTH_SHORT).show();
            }
            btnImam2.setOnClickListener {
                SaveSharedPreferences(requireContext()).ganti_qori = 1
                binding.textQori.text = "Abdullah Ibn Ali Basfar"
                dialog.dismiss()
                Toast.makeText(requireContext(), "Quran reader is set to Abdullah Ibn Ali Basfar", Toast.LENGTH_SHORT).show();
            }
            btnImam3.setOnClickListener {
                SaveSharedPreferences(requireContext()).ganti_qori = 2
                binding.textQori.text = "Abu Bakr Al Shatri"
                dialog.dismiss()
                Toast.makeText(requireContext(), "Quran reader is set to Abu Bakr Al Shatri", Toast.LENGTH_SHORT).show();
            }
            btnImam4.setOnClickListener {
                SaveSharedPreferences(requireContext()).ganti_qori = 3
                binding.textQori.text = "Ahmad bin Ali Al-Ajmi"
                dialog.dismiss()
                Toast.makeText(requireContext(), "Quran reader is set to Ahmad bin Ali Al-Ajmi", Toast.LENGTH_SHORT).show();
            }
            btnImam5.setOnClickListener {
                SaveSharedPreferences(requireContext()).ganti_qori = 4
                binding.textQori.text = "Mishari Alafasy"
                dialog.dismiss()
                Toast.makeText(requireContext(), "Quran reader is set to Mishari Alafasy", Toast.LENGTH_SHORT).show();
            }
            btnImam6.setOnClickListener {
                SaveSharedPreferences(requireContext()).ganti_qori = 5
                binding.textQori.text = "Ali Jaber"
                dialog.dismiss()
                Toast.makeText(requireContext(), "Quran reader is set to Ali Jabe", Toast.LENGTH_SHORT).show();
            }


            dialog.show()

        }

    }
}