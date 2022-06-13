package com.example.onlineshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
//        val imageView = findViewById<ImageView>(R.id.icon_splash_screen)
//        val fragment = findViewById<View>(R.id.fragmentContainerView2)
//        imageView.alpha=0f
//        imageView.animate().setDuration(3000).alpha(1f).withEndAction {
//            fragment.visibility=View.VISIBLE
//            imageView.visibility=View.GONE
//            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
//        }
    }
}