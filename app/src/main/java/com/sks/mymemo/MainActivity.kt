package com.sks.mymemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        //val navController = this.findNavController(R.id.nav_host_fragment)
        //val appBarConfiguration = AppBarConfiguration(navController.graph)
        //findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)
        //NavigationUI.setupActionBarWithNavController(this,navController)
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }*/
}