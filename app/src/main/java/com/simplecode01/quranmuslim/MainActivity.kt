package com.simplecode01.quranmuslim

import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lzx.starrysky.utils.KtPreferences.Companion.context
import com.simplecode01.quranmuslim.data.QuranDatabase
import com.simplecode01.quranmuslim.databinding.ActivityMainBinding
import com.simplecode01.quranmuslim.save.SaveSharedPreferences
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
                R.id.nav_quran, R.id.nav_contact, R.id.nav_about_us, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            var showToolbar: Boolean
            when(destination.id){//ketika tujuan fragmentnya main maka toolbar di hide
                R.id.nav_search_quran ->{
                    showToolbar = false
                }else ->{
                    showToolbar = true
                }
            }
            binding.toolbar.isVisible = showToolbar
        }


    }
    //        contohManggilDatabase()
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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}