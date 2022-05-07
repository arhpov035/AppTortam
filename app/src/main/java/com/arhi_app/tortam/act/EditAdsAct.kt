package com.arhi_app.tortam.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arhi_app.tortam.R
import com.arhi_app.tortam.databinding.ActivityEditAdsBinding

class EditAdsAct : AppCompatActivity() {
    private lateinit var rootElement: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
    }
}