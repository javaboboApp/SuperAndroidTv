package com.example.simpleandroidtv.ui.home

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.simpleandroidtv.R
/*
* In this example we create a 3 rows for 3 columns , we only show the pictures.
* */
class HomeActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}