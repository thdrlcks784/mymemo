package com.sks.mymemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

interface TempToolbarTitleListener{
    fun updateTitle(title: String)
}

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, TempToolbarTitleListener{

    lateinit var toolbar : Toolbar
    lateinit var appBarConfiguration : AppBarConfiguration
    lateinit var navController : NavController
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        //appBarConfiguration = AppBarConfiguration(navController.graph,drawer_layout,setOf(R.id.secretMemoListFragment))
        appBarConfiguration = AppBarConfiguration(setOf(R.id.memoListFragment, R.id.trashMemoFragment),drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        nav_view.setNavigationItemSelectedListener(this)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun updateTitle(title: String) {
        toolbar.title = title
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        when(item.itemId){
            R.id.memoListFragment ->{
                Log.d("TAG", "totalmemo click")
                navController.navigate(R.id.memoListFragment)
                toolbar.setBackgroundColor(Color.parseColor("#e9e9e9"))
                drawer_layout.closeDrawer(GravityCompat.START)
            }

            R.id.trashMemoFragment ->{
                Log.d("TAG","trashCan Click")
                navController.navigate(R.id.trashMemoFragment)
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}