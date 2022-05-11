package com.arhi_app.tortam.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arhi_app.tortam.R
import com.arhi_app.tortam.databinding.ActivityEditAdsBinding
import com.arhi_app.tortam.dialogs.DialogSpinnerHelper
import com.arhi_app.tortam.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    lateinit var rootElement: ActivityEditAdsBinding

    // создаём инстанцию класса DialogSpinnerHelper
    private val dialog = DialogSpinnerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        init()

    }

    private fun init() {



    }

    //OnClicks
    fun onClickSelectCountry(view: View) {
        // список стран
        val listCountry = CityHelper.getAllCountries(this)
        //запускаем
        dialog.showSpinnerDialog(this, listCountry, rootElement.tvCountry)
        if(rootElement.tvCity.text.toString() != getString(R.string.select_city)){
            rootElement.tvCity.text = getString(R.string.select_city)
        }
    }

    fun onClickSelectCity(view: View) {
        val selectedCountry = rootElement.tvCountry.text.toString()
        if (selectedCountry != getString(R.string.select_country)) {
            // список стран
            val listCity = CityHelper.getAllCities(selectedCountry, this)
            //запускаем
            dialog.showSpinnerDialog(this, listCity, rootElement.tvCity)
        }else{
            Toast.makeText(this, "No country selected", Toast.LENGTH_LONG).show()
        }
    }
}