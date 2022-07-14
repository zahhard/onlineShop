package com.example.onlineshop

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val bottomNavigationViewt = findViewById<BottomNavigationView>(R.id.navigation)
        val imageView = findViewById<ImageView>(R.id.lima)
        val fragment = findViewById<View>(R.id.fragmentContainerView2)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment?
        NavigationUI.setupWithNavController(bottomNavigationViewt, navHostFragment!!.navController)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        imageView.alpha = 0f
        fragment.alpha = 0f
        bottomNavigationViewt.alpha = 0f

        imageView.animate().setDuration(3000).alpha(1F).withEndAction {
            fragment.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            fragment.visibility = View.VISIBLE
            fragment.animate().setDuration(1000).alpha(1f).withEndAction {
            }
            bottomNavigationViewt.visibility = View.VISIBLE
            bottomNavigationViewt.animate().setDuration(1000).alpha(1f).withEndAction {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                bottomNavigationViewt.visibility = View.VISIBLE
            }
        }
    }
}