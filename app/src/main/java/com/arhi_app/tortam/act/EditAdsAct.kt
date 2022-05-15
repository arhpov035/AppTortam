package com.arhi_app.tortam.act

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arhi_app.tortam.R
import com.arhi_app.tortam.databinding.ActivityEditAdsBinding
import com.arhi_app.tortam.dialogs.DialogSpinnerHelper
import com.arhi_app.tortam.utils.CityHelper
import com.arhi_app.tortam.utils.ImagePicker
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil


class EditAdsAct : AppCompatActivity() {
    lateinit var rootElement: ActivityEditAdsBinding
    // создаём инстанцию класса DialogSpinnerHelper
    private val dialog = DialogSpinnerHelper()
    private var isImagesPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        init()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isImagesPermissionGranted = true
                } else {
                    isImagesPermissionGranted = false
                    Toast.makeText(
                        this,
                        "Approve permissions to open Pix ImagePicker",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
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

    fun onClickGetImages(view: View) {
        ImagePicker.getImages(this)
    }
}