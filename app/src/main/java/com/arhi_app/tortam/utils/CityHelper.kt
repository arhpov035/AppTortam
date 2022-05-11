package com.arhi_app.tortam.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

object CityHelper {
    // получаем список стран
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
            //достаём название этих объектов - метод .names() достает название массивов
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

    // получаем список городов
    fun getAllCities(country : String, context: Context): ArrayList<String> {
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
            // из json обхекта берем массивы, передаем по какому названию искать
            val cityNames = jsonObject.getJSONArray(country)
                for (n in 0 until cityNames.length()) {
                    tempArray.add(cityNames.getString(n))
                }

        } catch (e: IOException) {

        }
        return tempArray
    }

    // функция для фильтра списка
    // то что будет возвращать ": ArrayList<String>" отфильтрованный
    // передаём список "searchText" по которому ищим
    fun filterListData(list: ArrayList<String>, searchText: String?) : ArrayList<String>{
        // чтобы принять отфильтрованные данные создаём временный массив
        val tempList = ArrayList<String>()
        // этот массив на всякий слуйчай очищаем
        tempList.clear()
        if (searchText == null) {
            tempList.add("No result")
            return tempList
        }
        // фильтруем
        for (selection : String in list){
            //  делаем сверку
            if (selection.lowercase(Locale.ROOT).startsWith(searchText.lowercase(Locale.ROOT)))
                tempList.add(selection)
        }
        if (tempList.size == 0)tempList.add("No result")
        return tempList
    }

}