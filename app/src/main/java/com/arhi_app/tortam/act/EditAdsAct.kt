package com.arhi_app.tortam.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.arhi_app.tortam.R
import com.arhi_app.tortam.databinding.ActivityEditAdsBinding
import com.arhi_app.tortam.dialogs.DialogSpinnerHelper
import com.arhi_app.tortam.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    private lateinit var rootElement: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)

        // список стран
        val listCountry = CityHelper.getAllCountries(this)
        // создаём инстанцию класса DialogSpinnerHelper
        val dialog = DialogSpinnerHelper()
        //запускаем
        dialog.showSpinnerDialog(this, listCountry)
    }
}