package com.arhi_app.tortam.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object CityHelper {
    fun getAllCountries(context: Context): ArrayList<String> {
        var tempArray = ArrayList<String>()
        try {
            // указываем из какого файла будем считывать поток из байтов
            val inputStream: InputStream = context.assets.open("countriesToCities.json")
            // узнаём размер данного стрима
            val size: Int = inputStream.available()
            val bytesArray = ByteArray(size)
            // считываем и записываем в массив
            inputStream.read(bytesArray)
            // превращаем в объект String
            val jsonFile = String(bytesArray)
            // превращаем в специальный json объект
            val jsonObject = JSONObject(jsonFile)
            //достаём название этих объектов
            val countriesNames = jsonObject.names()
            if (countriesNames != null) {
                for (n in 0 until countriesNames.length()) {
                    tempArray.add(countriesNames.getString(n))
                }
            }

        } catch (e: IOException) {

        }
        return tempArray
    }
}