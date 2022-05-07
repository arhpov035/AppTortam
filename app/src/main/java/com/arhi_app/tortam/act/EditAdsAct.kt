package com.arhi_app.tortam.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.arhi_app.tortam.R
import com.arhi_app.tortam.databinding.ActivityEditAdsBinding
import com.arhi_app.tortam.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    private lateinit var rootElement: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        // создаём адаптер и подключаем к нашему спинеру
        // в него передаём контекс, разметку, массив
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            CityHelper.getAllCountries(this)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // берем наш спинер
        rootElement.spContry.adapter = adapter
    }
}