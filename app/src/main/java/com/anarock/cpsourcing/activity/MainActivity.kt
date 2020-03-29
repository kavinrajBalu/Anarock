package com.anarock.cpsourcing.activity

import android.content.pm.ActivityInfo
import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.ActivityMainBinding
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setSupportActionBar(binding.toolbar)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment? ?: return

        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.eventFragement, R.id.companyCode),
            drawerLayout
        )

        setupActionBar(navController, appBarConfiguration)


        val sharedViewModel = ViewModelProvider(this).get(SharedUtilityViewModel::class.java)

        sharedViewModel.getBottomNavigationVisibility().observe(this, Observer {
            if (it) {
                binding.bottomNavView.visibility = View.VISIBLE
            } else {
                binding.bottomNavView.visibility = View.GONE

            }
        })
      /*  Observer {
            if(it){
                binding.toolbar.navigationIcon= getDrawable(R.drawable.ic_back_black)
                binding.toolbar.background = ContextCompat.getDrawable(this, android.R.color.white)
                binding.toolbar.title = " "
            }else{
                binding.toolbar.navigationIcon= getDrawable(R.drawable.ic_back)
                binding.toolbar.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
            }
        }
*/
        sharedViewModel.getToolbarTheme().observe(this, Observer {
            if(!it.isFirstFragment)
                binding.toolbar.navigationIcon= getDrawable(R.drawable.ic_back_black)
            else
                binding.toolbar.navigationIcon= null

            if(it.isLightTheme){
                binding.toolbar.background = ContextCompat.getDrawable(this, android.R.color.white)
                binding.toolbar.title = " "

                window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));

            }else{
                binding.toolbar.navigationIcon= getDrawable(R.drawable.ic_back)
                binding.toolbar.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }

        })

        sharedViewModel.getToolBarVisibility().observe(this, Observer {
            if(it)
            {
                supportActionBar?.show()
            }
            else
            {
                supportActionBar?.hide()
            }

        })

        sharedViewModel.getCustomToolBar().observe(this, Observer {
            binding.toolbar.background = ContextCompat.getDrawable(this,it.background)
            binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, it.titleColor))
        })

        sharedViewModel.getCustomStatusBar().observe(this, Observer {
            if(it == android.R.color.white)
            {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            else
            {
                window.decorView.systemUiVisibility = 0
            }
            window.statusBarColor = ContextCompat.getColor(this, it);
        })

        setUpBottomNavigationMenu(navController,binding.bottomNavView)
    }

    private fun setUpBottomNavigationMenu(
        navController: NavController,
        bottomNavView: BottomNavigationView
    ) {
        val badge: BadgeDrawable = bottomNavView.getOrCreateBadge(binding.bottomNavView.menu[2].itemId)
        badge.number = 10
        bottomNavView.setupWithNavController(navController)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_container).navigateUp()
    }
}
