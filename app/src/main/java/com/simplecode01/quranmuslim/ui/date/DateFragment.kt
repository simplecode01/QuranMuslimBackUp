package com.simplecode01.quranmuslim.ui.date

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.dataapiretro.apiDate.HijrihDateAPI
import com.simplecode01.quranmuslim.databinding.FragmentJamSholatBinding
import java.text.SimpleDateFormat
import java.util.*

import mumayank.com.airlocationlibrary.AirLocation
import android.location.Location
import android.widget.TextView
import android.widget.Toast
import com.simplecode01.quranmuslim.dataapiretro.apiPrayer.PrayerAPI
import kotlinx.coroutines.launch

class DateFragment: Fragment(R.layout.fragment_jam_sholat) {

    private lateinit var airlocation: AirLocation

    private val binding: FragmentJamSholatBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = HijrihDateAPI.create()
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        val tme = SimpleDateFormat("HH:mm")
        val currentTime = tme.format(Date())

        lifecycleScope.launchWhenCreated {
            val response = api.getHijrihDate("${currentDate}", 1)
            val hijrihDates = response.data.hijri
            val gregoDates = response.data.gregorian

            val dateFormat = gregoDates.weekday.en

            binding.tvHariHijri.text = "${hijrihDates.weekday.en}, "

            binding.tvDate.text = "${hijrihDates.day} ${hijrihDates.month.en} ${hijrihDates.year}"

            if(dateFormat == "Monday") {
                binding.tvHari.text = "Senin, "
            }else if(dateFormat == "Tuesday"){
                binding.tvHari.text = "Selasa, "
            }else if(dateFormat == "Wednesday"){
                binding.tvHari.text = "Rabu, "
            }else if(dateFormat == "Thursday"){
                binding.tvHari.text = "Kamis, "
            }else if(dateFormat == "Friday"){
                binding.tvHari.text = "Jumat, "
            }else if(dateFormat == "Saturday"){
                binding.tvHari.text = "Sabtu, "
            }else if(dateFormat == "Sunday"){
                binding.tvHari.text = "Minggu, "
            }
            binding.tvTanggalGre.text = "${gregoDates.day} ${gregoDates.month.en} ${gregoDates.year} ${currentTime}"
        }

        airlocation = AirLocation(requireActivity(), object : AirLocation.Callback {
            override fun onSuccess(locations: ArrayList<Location>) {
                val api = PrayerAPI.create()


                val textFajr = view.findViewById<TextView>(R.id.tv_subuh)
                val textDzuhur = view.findViewById<TextView>(R.id.tv_dzuhur)
                val textAshr = view.findViewById<TextView>(R.id.tv_ashar)
                val textMgrib = view.findViewById<TextView>(R.id.tv_maghrib)
                val textIsy = view.findViewById<TextView>(R.id.tv_isya)

                lifecycleScope.launch {
                    val response = api.getPrayerTime(locations[0].latitude, locations[0].longitude)
                    val prayerTime = response.results.datetime[0].times

                    val tme = SimpleDateFormat("HH:mm")
                    val currentTime = tme.format(Date())

                    textFajr.text = prayerTime.fajr
                    textDzuhur.text = prayerTime.dhuhr
                    textAshr.text = prayerTime.asr
                    textMgrib.text = prayerTime.maghrib
                    textIsy.text = prayerTime.isha

                    if(prayerTime.fajr == currentTime){

                    }else if(prayerTime.dhuhr == currentTime){

                    }else if(prayerTime.asr == currentTime){

                    }else if(prayerTime.maghrib == currentTime){

                    }else if(prayerTime.isha == currentTime){

                    }
                }
            }
            override fun onFailure(locationFailedEnum: AirLocation.LocationFailedEnum) {
                Toast.makeText(requireContext(), locationFailedEnum.name, Toast.LENGTH_SHORT).show()
            }
        },true)
        airlocation.start()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        airlocation.onActivityResult(requestCode, resultCode, data)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        airlocation.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}