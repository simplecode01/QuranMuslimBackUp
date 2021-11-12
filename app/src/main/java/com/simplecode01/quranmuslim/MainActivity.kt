package com.simplecode01.quranmuslim

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.lzx.starrysky.utils.KtPreferences.Companion.context
import com.simplecode01.quranmuslim.data.QuranDatabase
import com.simplecode01.quranmuslim.dataapiretro.apiPrayer.PrayerAPI
import com.simplecode01.quranmuslim.databinding.ActivityMainBinding
import com.simplecode01.quranmuslim.save.SaveSharedPreferences
import kotlinx.coroutines.launch
import mumayank.com.airlocationlibrary.AirLocation
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: ActivityMainBinding by viewBinding(R.id.drawer_layout)

    override fun onCreate(savedInstanceState: Bundle?) {


        when(SaveSharedPreferences(context!!).darkMode){
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_quran
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomAppBar.setupWithNavController(navController)
        navView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            var showToolbar: Boolean = true
            var showBottom: Boolean = true

            when(destination.id){//ketika tujuan fragmentnya main maka toolbar di hide
                R.id.nav_search_quran ->{
                    showToolbar = false
                }
                R.id.nav_about_us ->{
                    showBottom = false
                }
                R.id.nav_rating ->{
                    showBottom = false
                }
                R.id.nav_feedback ->{
                    showBottom = false
                }
                R.id.nav_read_rules ->{
                    showBottom = false
                }
                R.id.nav_qibla ->{
                    showBottom = false
                }
                R.id.nav_read_quran ->{
                    showBottom = false
                }
                else ->{
                    showToolbar = true
                }
            }
            binding.toolbar.isVisible = showToolbar

            binding.btmAppBarMain.isVisible = showBottom
        }
    }
    private fun contohManggilDatabase(){
        val database = QuranDatabase.getInstance(this)
        val quran = database.quranDao().getQuran()
        quran.asLiveData().observe(this, { listQuran ->
          Log.d("CEK JUMLAH", listQuran.size.toString())
        })
    }

    private fun ManggilJuzDatabase(){
        val database = QuranDatabase.getInstance(this)
        val juz = database.quranDao().getJuz()
        juz.asLiveData().observe(this, { listSurah ->
            Log.d("CEK Juz", listSurah.size.toString())
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}